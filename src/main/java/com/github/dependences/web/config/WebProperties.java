package com.github.dependences.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jackwu
 */
@Data
@ConfigurationProperties("web")
public class WebProperties {

    /**
     * 基础包路径
     */
    private String basePackage;

    /**
     * api 的统一前缀
     */
    private String apiPrefix;
}
