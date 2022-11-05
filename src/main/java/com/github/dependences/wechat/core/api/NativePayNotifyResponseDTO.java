package com.github.dependences.wechat.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * native支付 - 下单回调 - response DTO
 *
 * @author jackwu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NativePayNotifyResponseDTO {

    /**
     * 返回状态码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;
}
