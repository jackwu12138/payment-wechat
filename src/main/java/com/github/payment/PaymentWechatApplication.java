package com.github.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 支付项目的后端启动类
 *
 * @author jackwu
 */
@SpringBootApplication
public class PaymentWechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentWechatApplication.class, args);
    }
}
