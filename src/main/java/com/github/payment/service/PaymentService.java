package com.github.payment.service;

import com.github.payment.controller.payment.vo.PrepaymentResponseVO;

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
}
