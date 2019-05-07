package com.study.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Lon
 * @Date: 2019/5/7 16:51
 * @Description:
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value(value = "${swagger.enabled}")
    Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
        // 是否开启
        .enable(swaggerEnabled).select()
        // 扫描的路径包
        .apis(RequestHandlerSelectors.basePackage("com.study.demo"))
        // 指定路径处理PathSelectors.any()代表所有的路径
        .paths(PathSelectors.any()).build().pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("接口调试文档").description("Lon_study_demo").build();
    }
}
