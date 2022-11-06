package com.github.payment.controller.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackwu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoResponseVO {

    /**
     * 订单标题
     */
    private String title;

    /**
     * 商户订单编号
     */
    private String orderNo;

    /**
     * 支付产品id
     */
    private Long productId;

    /**
     * 订单金额(分)
     */
    private Integer totalFee;

    /**
     * 订单状态
     */
    private String orderStatus;
}
