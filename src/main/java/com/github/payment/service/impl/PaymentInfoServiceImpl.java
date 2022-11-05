package com.github.payment.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.dependences.wechat.core.api.NativeQueryResponseDTO;
import com.github.payment.databject.PaymentInfoDO;
import com.github.payment.mapper.PaymentInfoMapper;
import com.github.payment.service.PaymentInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 支付信息的 service 实现类
 *
 * @author jackwu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final PaymentInfoMapper baseMapper;

    @Override
    public void createPaymentInfo(NativeQueryResponseDTO data) {
        log.info("记录支付日志");
        PaymentInfoDO paymentInfo = PaymentInfoDO.builder()
                .orderNo(data.getOutTradeNo())
                .paymentType("wechat")
                .transactionId(data.getTransactionId())
                .tradeType(data.getTradeType())
                .tradeState(data.getTradeState())
                .payerTotal(data.getAmount().getPayerTotal())
                .content(JSONUtil.toJsonStr(data))
                .build();
        baseMapper.insert(paymentInfo);
    }
}
