package com.github.payment.databject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 付款信息
 *
 * @author jackwu
 */
@Data
@Builder
@TableName("t_payment_info")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentInfoDO extends BaseDO {

    /**
     * 商品订单编号
     */
    private String orderNo;

    /**
     * 支付系统交易编号
     */
    private String transactionId;

    /**
     * 支付类型
     */
    private String paymentType;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 交易状态
     */
    private String tradeState;

    /**
     * 支付金额(分)
     */
    private Integer payerTotal;

    /**
     * 通知参数
     */
    private String content;
}
