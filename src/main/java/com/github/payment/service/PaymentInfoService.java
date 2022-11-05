package com.github.payment.service;

import com.github.dependences.wechat.core.api.NativeQueryResponseDTO;

/**
 * 支付信息的 service 接口
 *
 * @author jackwu
 */
public interface PaymentInfoService {

    /**
     * 创建支付日志
     *
     * @param encryptedData 支付信息
     */
    void createPaymentInfo(NativeQueryResponseDTO encryptedData);
}
