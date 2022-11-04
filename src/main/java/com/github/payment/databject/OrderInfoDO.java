package com.github.payment.databject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.dependences.mybatisplus.core.BaseDO;
import lombok.*;

/**
 * 订单信息
 *
 * @author jackwu
 */
@Data
@Builder
@TableName("t_order_info")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderInfoDO extends BaseDO {

    /**
     * 订单标题
     */
    private String title;

    /**
     * 商户订单编号
     */
    private String orderNo;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 支付产品id
     */
    private Long productId;

    /**
     * 订单金额(分)
     */
    private Integer totalFee;

    /**
     * 订单二维码连接
     */
    private String codeUrl;

    /**
     * 订单状态
     */
    private String orderStatus;
}
