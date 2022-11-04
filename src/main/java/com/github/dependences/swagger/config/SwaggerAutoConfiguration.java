package com.github.dependences.swagger.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/**
 * Swagger 相关的配置类
 *
 * @author jackwu
 */
@Slf4j
@EnableOpenApi
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    @Bean
    public Docket docket(SwaggerProperties properties) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(properties))
                .enable(true)
                .select()
                .apis(basePackage(properties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Api 摘要信息
     */
    public ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .contact(new Contact(properties.getAuthor(), null, null))
                .version(properties.getVersion())
                .build();
    }
}
