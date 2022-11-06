package com.github.payment.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.github.common.core.constants.OrderStatusConstants;
import com.github.dependences.wechat.config.WxPayProperties;
import com.github.dependences.wechat.core.api.*;
import com.github.dependences.wechat.core.constants.wechat.WxNotifyConstants;
import com.github.dependences.wechat.core.service.NativePayService;
import com.github.payment.controller.payment.vo.PrepaymentResponseVO;
import com.github.payment.databject.OrderInfoDO;
import com.github.payment.service.OrderInfoService;
import com.github.payment.service.PaymentService;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import static com.github.common.core.util.ExceptionUtil.exception;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * 支付的 service 实现类
 *
 * @author jackwu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final NativePayService nativePayService;

    private final OrderInfoService orderInfoService;

    private final WxPayProperties wxPayProperties;

    @Override
    public PrepaymentResponseVO nativePrepayment(Long productId) {
        log.info(">>> 发起 native 下单请求 <<<");
        OrderInfoDO orderInfo = orderInfoService.createOrderInfoById(productId);
        // 创建请求参数
        NativePayRequestDTO param = createNativePayParam(orderInfo);
        // 发起请求并拿到响应结果
        NativePayResponseDTO nativePayResponse = nativePayService.nativePay(param);
        // 拿到响应结果中的二维码链接, 并更新订单信息的二维码链接
        String codeUrl = nativePayResponse.getCodeUrl();
        orderInfo.setCodeUrl(codeUrl);
        orderInfoService.updateCodeUrl(orderInfo);
        // 返回响应结果
        return buildPrepaymentResponseVO(codeUrl, orderInfo.getOrderNo());
    }

    @Override
    public ResponseEntity<NativePayNotifyResponseDTO> nativePaymentNotify(HttpServletRequest request) {
        log.info(">>> 收到 native 下单回调通知 <<<");
        // 解析微信发送的请求参数
        String body = ServletUtil.getBody(request);
        log.debug("微信发送的请求参数: {}", body);
        NativePayNotifyRequestDTO param = JSONUtil.toBean(body, NativePayNotifyRequestDTO.class);
        String requestId = param.getId();

        // 对签名进行验证
        if (!nativePayService.isRequestValidate(body, requestId, request)) {
            // 返回验签失败的通知信息
            NativePayNotifyResponseDTO responseDTO =
                    new NativePayNotifyResponseDTO("FAIL", "验签失败");
            return new ResponseEntity<>(responseDTO, INTERNAL_SERVER_ERROR);
        }

        // 获取回调参数中的加密数据
        NativeQueryResponseDTO encryptedData = getEncryptedData(param.getResource());
        // 订单付款成功后更新订单数据
        orderInfoService.updateOrderWhenPaymentSuccess(encryptedData);

        return new ResponseEntity<>(new NativePayNotifyResponseDTO(), NO_CONTENT);
    }

    @Override
    public void cancelOrder(String orderNo) {
        log.info(">>> 发起 native 取消订单请求 <<<");
        // 校验订单是否存在
        validateOrderNoExists(orderNo);
        // 向微信发送取消订单的请求
        nativePayService.closePaymentOrder(orderNo);
        // 更新商户端的订单状态
        orderInfoService.updateStatusByOrderNo(orderNo, OrderStatusConstants.CANCEL);
    }

    @Override
    public NativeQueryResponseDTO queryOrder(String orderNo) {
        log.info(">>> 发起 native 查询订单请求 <<<");
        return nativePayService.queryPaymentOrder(orderNo);
    }

    /**
     * 对订单信息进行处理
     *
     * @param resource 加密数据
     * @return 解析后的明文信息数据
     */
    private NativeQueryResponseDTO getEncryptedData(NativePayNotifyRequestDTO.ResourceDTO resource) {
        // 解密报文
        log.debug("--->>> 开始对数据进行解密 <<<---");

        AesUtil aesUtil = new AesUtil(wxPayProperties.getApiV3Key().getBytes());
        byte[] associatedData = resource.getAssociatedData().getBytes(StandardCharsets.UTF_8);
        byte[] nonce = resource.getNonce().getBytes(StandardCharsets.UTF_8);
        String ciphertext = resource.getCiphertext();
        log.debug("->>> 解密前的密文数据:{}", ciphertext);
        String plaintext;
        try {
            plaintext = aesUtil.decryptToString(associatedData, nonce, ciphertext);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        log.debug("->>> 解密后的明文数据: {}", plaintext);

        log.debug("--->>> 数据解密完成 <<<---");
        return JSONUtil.toBean(plaintext, NativeQueryResponseDTO.class);
    }

    /**
     * 校验订单号是否存在
     *
     * @param orderNo 要进行校验的订单号
     */
    private void validateOrderNoExists(String orderNo) {
        OrderInfoDO orderInfo = orderInfoService.getOrderByOrderNo(orderNo);
        if (ObjectUtil.isNull(orderInfo)) {
            throw exception("订单号不存在");
        }
        if (!StrUtil.equals(orderInfo.getOrderStatus(), OrderStatusConstants.NOTPAY)) {
            throw exception("订单不能被取消");
        }
    }

    /**
     * 创建向微信发送的下单参数
     *
     * @param orderInfo 订单信息
     * @return 创建好的下单参数
     */
    private NativePayRequestDTO createNativePayParam(OrderInfoDO orderInfo) {
        NativePayRequestDTO.Amount amount = NativePayRequestDTO.Amount.builder()
                .total(orderInfo.getTotalFee())
                .build();

        return NativePayRequestDTO.builder()
                .appid(wxPayProperties.getAppid())
                .mchid(wxPayProperties.getMchId())
                .description(orderInfo.getTitle())
                .outTradeNo(orderInfo.getOrderNo())
                .notifyUrl(wxPayProperties.getNotifyDomain() + WxNotifyConstants.NATIVE_NOTIFY)
                .amount(amount)
                .build();
    }

    /**
     * 组装预下载的 response VO
     *
     * @return 组装好的 response VO
     */
    private PrepaymentResponseVO buildPrepaymentResponseVO(String codeUrl, String orderNo) {
        return PrepaymentResponseVO.builder().codeUrl(codeUrl).orderNo(orderNo).build();
    }
}
