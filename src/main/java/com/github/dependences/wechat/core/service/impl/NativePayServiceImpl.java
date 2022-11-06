package com.github.dependences.wechat.core.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.github.common.core.util.JsonUtil;
import com.github.dependences.wechat.config.WxPayProperties;
import com.github.dependences.wechat.core.api.NativeCloseRequestDTO;
import com.github.dependences.wechat.core.api.NativePayRequestDTO;
import com.github.dependences.wechat.core.api.NativePayResponseDTO;
import com.github.dependences.wechat.core.api.NativeQueryResponseDTO;
import com.github.dependences.wechat.core.constants.wechat.WxApiConstants;
import com.github.dependences.wechat.core.service.NativePayService;
import com.github.dependences.wechat.core.util.WechatPay2ValidatorForRequest;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.github.dependences.wechat.core.constants.wechat.WxApiConstants.NATIVE_PAY;
import static com.github.dependences.wechat.core.constants.wechat.WxApiConstants.ORDER_QUERY_BY_NO;

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

    private final Verifier verifier;

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

    @Override
    public void closePaymentOrder(String orderNo) {
        String url = properties.getDomain() +
                StrUtil.format(WxApiConstants.CLOSE_ORDER_BY_NO, orderNo);
        String params = JSONUtil.toJsonStr(new NativeCloseRequestDTO(properties.getMchId()));
        try (CloseableHttpResponse response = doPost(url, params)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.HTTP_OK || statusCode == HttpStatus.HTTP_NO_CONTENT) {
                log.debug("关闭订单成功! 响应码:'{}'", statusCode);
            } else {
                log.error("关闭订单失败! 响应码:'{}'", statusCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NativeQueryResponseDTO queryPaymentOrder(String orderNo) {
        String url = properties.getDomain()
                + StrUtil.format(ORDER_QUERY_BY_NO, orderNo)
                + "?mchid="
                + properties.getMchId();

        try (CloseableHttpResponse response = doGet(url)) {
            String body = EntityUtils.toString(response.getEntity());
            NativeQueryResponseDTO queryResponse = JSONUtil.toBean(body, NativeQueryResponseDTO.class);
            log.info("查单结果: {}", body);
            return queryResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isRequestValidate(String body, String requestId, HttpServletRequest request) {
        WechatPay2ValidatorForRequest validator =
                new WechatPay2ValidatorForRequest(verifier, body, requestId);
        try {
            return validator.validate(request);
        } catch (IOException e) {
            log.error("解析签名出错");
            return false;
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

    /**
     * get 方式请求微信的接口
     *
     * @param url 请求的地址
     * @return 响应的信息
     */
    private CloseableHttpResponse doGet(String url) {
        log.debug("请求方式: [get], 请求地址: [{}]", url);
        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader(Header.ACCEPT.getValue(), ContentType.JSON.getValue());

        try {
            return wxPayHttpClient.execute(httpGet);
        } catch (IOException e) {
            log.error("请求微信接口出错", e);
            throw new RuntimeException(e);
        }
    }
}
