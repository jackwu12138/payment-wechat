package com.github.dependences.wechat.config;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.ScheduledUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

/**
 * 微信支付的相关配置类
 *
 * @author jackwu
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayAutoConfiguration {

    private final WxPayProperties properties;

    /**
     * 读取私钥文件
     */
    private PrivateKey getPrivateKey(String filename) {
        try {
            return PemUtil.loadPrivateKey(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("私钥文件不存在", e);
        }
    }

    /**
     * 获取签名验证器
     */
    @Bean
    public ScheduledUpdateCertificatesVerifier getVerifier() {
        //获取商户私钥
        PrivateKey privateKey = getPrivateKey(properties.getPrivateKeyPath());
        //私钥签名对象
        PrivateKeySigner privateKeySigner = new PrivateKeySigner(properties.getMchSerialNo(), privateKey);
        //身份认证对象
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(properties.getMchId(), privateKeySigner);
        // 使用定时更新的签名验证器，不需要传入证书
        return new ScheduledUpdateCertificatesVerifier(
                wechatPay2Credentials,
                properties.getApiV3Key().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取http请求对象
     */
    @Bean
    public CloseableHttpClient getWxPayClient(ScheduledUpdateCertificatesVerifier verifier) {
        //获取商户私钥
        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        PrivateKey privateKey = getPrivateKey(properties.getPrivateKeyPath());
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(properties.getMchId(), properties.getMchSerialNo(), privateKey)
                .withValidator(new WechatPay2Validator(verifier));
        return builder.build();
    }
}
