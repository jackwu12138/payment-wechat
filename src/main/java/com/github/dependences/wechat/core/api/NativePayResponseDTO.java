package com.github.dependences.wechat.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * native支付 - 下单 - response DTO
 *
 * @author jackwu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NativePayResponseDTO {

    /**
     * 二维码链接
     */
    private String codeUrl;
}
