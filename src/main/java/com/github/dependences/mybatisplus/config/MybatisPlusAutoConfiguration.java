package com.github.dependences.mybatisplus.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus 相关的配置类
 *
 * @author jackwu
 */
@Configuration
@EnableTransactionManagement
@MapperScan(annotationClass = Mapper.class, basePackages = "com.github.payment.mapper")
public class MybatisPlusAutoConfiguration {}
