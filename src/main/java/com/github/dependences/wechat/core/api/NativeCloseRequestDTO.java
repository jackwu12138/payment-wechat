package com.github.dependences.wechat.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * native支付 - 退单 - request DTO
 *
 * @author jackwu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NativeCloseRequestDTO {

    /**
     * 商户号
     */
    private String mchid;
}
