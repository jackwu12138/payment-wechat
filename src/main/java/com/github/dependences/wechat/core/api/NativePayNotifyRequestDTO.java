package com.github.dependences.wechat.core.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * native支付 - 下单回调 - request DTO
 *
 * @author jackwu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NativePayNotifyRequestDTO {

    /**
     * 通知 id
     */
    private String id;

    /**
     * 通知创建时间
     */
    @JsonProperty("create_time")
    private String createTime;

    /**
     * 通知类型
     */
    @JsonProperty("event_type")
    private String eventType;

    /**
     * 通知数据类型
     */
    @JsonProperty("resource_type")
    private String resourceType;

    /**
     * 回调摘要
     */
    private String summary;

    /**
     * 通知数据
     */
    private ResourceDTO resource;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResourceDTO {

        /**
         * 原始类型
         */
        @JsonProperty("original_type")
        private String originalType;

        /**
         * 加密算法类型
         */
        private String algorithm;

        /**
         * 数据密文
         */
        private String ciphertext;

        /**
         * 附加数据
         */
        @JsonProperty("associated_data")
        private String associatedData;

        /**
         * 随机串
         */
        private String nonce;
    }
}
