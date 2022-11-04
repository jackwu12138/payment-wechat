package com.github.dependences.wechat.core.constants.wechat;

/**
 * 微信的 api 常量
 *
 * @author jackwu
 */
public interface WxApiConstants {

    /**
     * Native下单
     */
    String NATIVE_PAY = "/v3/pay/transactions/native";

    /**
     * Native下单( v2版本)
     */
    String NATIVE_PAY_V2 = "/pay/unifiedorder";

    /**
     * 查询订单
     */
    String ORDER_QUERY_BY_NO = "/v3/pay/transactions/out-trade-no/{}";

    /**
     * 关闭订单
     */
    String CLOSE_ORDER_BY_NO = "/v3/pay/transactions/out-trade-no/{}/close";

    /**
     * 申请退款
     */
    String DOMESTIC_REFUNDS = "/v3/refund/domestic/refunds";

    /**
     * 查询单笔退款
     */
    String DOMESTIC_REFUNDS_QUERY = "/v3/refund/domestic/refunds/%s";

    /**
     * 申请交易账单
     */
    String TRADE_BILLS = "/v3/bill/tradebill";

    /**
     * 申请资金账单
     */
    String FUND_FLOW_BILLS = "/v3/bill/fundflowbill";
}
