package com.github.dependences.wechat.core.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * native 支付 - 查单 - response DTO
 *
 * @author jackwu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NativeQueryResponseDTO {

    /**
     * 应用 ID
     */
    private String appid;

    /**
     * 直连商户号
     */
    private String mchid;

    /**
     * 商户订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 微信支付订单号
     */
    @JsonProperty("transaction_id")
    private String transactionId;

    /**
     * 交易类型
     */
    @JsonProperty("trade_type")
    private String tradeType;

    /**
     * 交易状态
     */
    @JsonProperty("trade_state")
    private String tradeState;

    /**
     * 交易状态描述
     */
    @JsonProperty("trade_state_desc")
    private String tradeStateDesc;

    /**
     * 银行类型
     */
    @JsonProperty("bank_type")
    private String bankType;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 支付完成时间
     */
    @JsonProperty("success_time")
    private String successTime;

    /**
     * 支付者
     */
    private PayerDTO payer;

    /**
     * 订单金额
     */
    private AmountDTO amount;

    /**
     * 场景信息
     */
    @JsonProperty("scene_info")
    private SceneDTO sceneInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayerDTO {

        /**
         * 用户标识
         */
        private String openid;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AmountDTO {

        /**
         * 总金额
         */
        private Integer total;

        /**
         * 用户支付金额
         */
        @JsonProperty("payer_total")
        private Integer payerTotal;

        /**
         * 货币类型
         */
        @JsonProperty
        private String currency;

        /**
         * 用户支付币种
         */
        @JsonProperty("payer_currency")
        private String payerCurrency;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SceneDTO {

        /**
         * 商户端设备号
         */
        @JsonProperty("device_id")
        private String deviceId;
    }
}
