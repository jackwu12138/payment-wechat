package com.github.payment.service;

import com.github.dependences.wechat.core.api.NativePayNotifyResponseDTO;
import com.github.payment.controller.payment.vo.PrepaymentResponseVO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付的 service 接口
 *
 * @author jackwu
 */
public interface PaymentService {

    /**
     * 预支付并返回支付信息
     *
     * @param productId 商品编号
     * @return 预支付信息
     */
    PrepaymentResponseVO nativePrepayment(Long productId);

    /**
     * 支付成功后的回调接口
     *
     * @param request 微信发送的请求信息
     * @return 响应给微信服务器的信息
     */
    ResponseEntity<NativePayNotifyResponseDTO> nativePaymentNotify(HttpServletRequest request);

    /**
     * 用户取消订单
     *
     * @param orderNo 要取消的订单编号
     */
    void cancelOrder(String orderNo);
}
