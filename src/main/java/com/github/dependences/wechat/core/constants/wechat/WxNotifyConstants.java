package com.github.dependences.wechat.core.constants.wechat;

/**
 * 微信通知的 api 常量
 *
 * @author jackwu
 */
public interface WxNotifyConstants {

    /**
     * 支付通知
     */
    String NATIVE_NOTIFY = "/api/wx-pay/native/notify";

    /**
     * 支付通知
     */
    String NATIVE_NOTIFY_V2 = "/api/wx-pay-v2/native/notify";

    /**
     * 退款结果通知
     */
    String REFUND_NOTIFY = "/api/wx-pay/refunds/notify";
}
