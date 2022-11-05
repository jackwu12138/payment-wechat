package com.github.payment.service.impl;

import com.github.dependences.wechat.config.WxPayProperties;
import com.github.dependences.wechat.core.api.NativePayRequestDTO;
import com.github.dependences.wechat.core.api.NativePayResponseDTO;
import com.github.dependences.wechat.core.constants.wechat.WxNotifyConstants;
import com.github.dependences.wechat.core.service.NativePayService;
import com.github.payment.controller.payment.vo.PrepaymentResponseVO;
import com.github.payment.databject.OrderInfoDO;
import com.github.payment.service.OrderInfoService;
import com.github.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
