package com.github.dependences.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;

/**
 * @author jackwu
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 作者
     */
    private String author;

    /**
     * 版本
     */
    private String version;

    /**
     * 扫描的包
     */
    private String basePackage;
}
