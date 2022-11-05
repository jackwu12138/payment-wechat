package com.github.dependences.wechat.core.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * native 支付 - 下单 - request DTO
 *
 * @author jackwu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NativePayRequestDTO {

    /**
     * app id
     */
    private String appid;

    /**
     * 商户信息
     */
    private String mchid;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 通知 URL
     */
    private String notifyUrl;

    /**
     * 价格信息
     */
    private Amount amount;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Amount {

        /**
         * 价格
         */
        private Integer total;

        /**
         * 币种
         */
        private String currency;
    }
}
