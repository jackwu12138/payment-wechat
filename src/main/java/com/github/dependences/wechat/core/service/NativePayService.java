package com.github.dependences.wechat.core.service;

import com.github.dependences.wechat.core.api.NativePayRequestDTO;
import com.github.dependences.wechat.core.api.NativePayResponseDTO;

/**
 * native 支付的 service 接口
 *
 * @author jackwu
 */
public interface NativePayService {

    /**
     * 预支付, 并生成支付二维码
     *
     * @param param 请求参数
     * @return 微信的响应结果
     */
    NativePayResponseDTO nativePay(NativePayRequestDTO param);
}
