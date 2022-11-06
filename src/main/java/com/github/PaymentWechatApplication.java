package com.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 支付项目的后端启动类
 *
 * @author jackwu
 */
@EnableScheduling
@SpringBootApplication
public class PaymentWechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentWechatApplication.class, args);
    }
}
