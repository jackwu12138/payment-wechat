package com.github.common.core.constants;

/**
 * 订单状态的常量类
 *
 * @author jackwu
 */
public interface OrderStatusConstants {

    /**
     * 未支付
     */
    String NOTPAY = "未支付";

    /**
     * 支付成功
     */
    String SUCCESS = "支付成功";

    /**
     * 已关闭
     */
    String CLOSED = "超时已关闭";

    /**
     * 已取消
     */
    String CANCEL = "用户已取消";

    /**
     * 退款中
     */
    String REFUND_PROCESSING = "退款中";

    /**
     * 已退款
     */
    String REFUND_SUCCESS = "已退款";

    /**
     * 退款异常
     */
    String REFUND_ABNORMAL = "退款异常";
}
