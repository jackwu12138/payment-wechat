package com.github.dependences.wechat.core.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.github.common.core.util.JsonUtil;
import com.github.dependences.wechat.config.WxPayProperties;
import com.github.dependences.wechat.core.api.NativePayRequestDTO;
import com.github.dependences.wechat.core.api.NativePayResponseDTO;
import com.github.dependences.wechat.core.service.NativePayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.github.dependences.wechat.core.constants.wechat.WxApiConstants.NATIVE_PAY;

/**
 * native 支付的 service 实现类
 *
 * @author jackwu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NativePayServiceImpl implements NativePayService {

    private final WxPayProperties properties;

    private final CloseableHttpClient wxPayHttpClient;

    @Override
    public NativePayResponseDTO nativePay(NativePayRequestDTO param) {
        String url = properties.getDomain() + NATIVE_PAY;
        String paramStr = JsonUtil.toUnderlineCaseJson(param);

        try (CloseableHttpResponse response = doPost(url, paramStr)) {
            // 得到响应体和响应结果
            String body = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.HTTP_OK) {
                log.debug("请求成功, 响应结果 ---> {}", body);
            } else if (statusCode == HttpStatus.HTTP_NO_CONTENT) {
                log.debug("请求成功");
            } else {
                log.error("Native下单失败, 响应码: [{}], 响应结果: {}", statusCode, body);
            }
            // 将响应体转换为对应的类型返回
            return JSONUtil.toBean(body, NativePayResponseDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * post 方式请求微信的接口
     *
     * @param url   请求的地址
     * @param param 请求的参数
     * @return 响应的信息
     */
    private CloseableHttpResponse doPost(String url, String param) {
        log.debug("请求方式: [post], 请求地址: [{}]", url);
        HttpPost httpPost = new HttpPost(url);

        if (StrUtil.isNotEmpty(param)) {
            log.debug("请求参数: {}", param);
            StringEntity entity = new StringEntity(param, StandardCharsets.UTF_8);
            entity.setContentType(ContentType.JSON.getValue());
            httpPost.setEntity(entity);
        }

        httpPost.setHeader(Header.ACCEPT.getValue(), ContentType.JSON.getValue());

        try {
            return wxPayHttpClient.execute(httpPost);
        } catch (IOException e) {
            log.error("请求微信接口出错", e);
            throw new RuntimeException(e);
        }
    }
}
