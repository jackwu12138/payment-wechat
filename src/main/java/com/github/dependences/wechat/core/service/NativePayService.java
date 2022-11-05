package com.github.dependences.wechat.core.service;

import com.github.dependences.wechat.core.api.NativePayRequestDTO;
import com.github.dependences.wechat.core.api.NativePayResponseDTO;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 判断请求签名是否有效
     *
     * @param body      请求体
     * @param requestId 请求的 requestId
     * @param request   接收到的请求
     * @return 签名是否有效
     */
    boolean isRequestValidate(String body, String requestId, HttpServletRequest request);
}
