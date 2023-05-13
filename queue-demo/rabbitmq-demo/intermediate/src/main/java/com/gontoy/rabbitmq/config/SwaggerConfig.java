package com.gontoy.rabbitmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;

/**
 * @author gzw
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
public Docket webApiConfig(){
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("webApi")
            .apiInfo(webApiInfo())
            .select()
            .build();
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("RabbitMQ 接口文档")
                .description("本文档描述了 rabbitmq 微服务接口定义")
                .version("1.0")
                .contact(new Contact("enjoy6288", "http://gontoy.com", "1551388580@qq.com"))
                .build();
    }
}