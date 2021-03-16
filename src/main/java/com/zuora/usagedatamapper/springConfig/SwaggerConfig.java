package com.zuora.usagedatamapper.springConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zuora.usagedatamapper.controllers"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "UsageDataMapper Configurations API",
                "APIs to transform customer specific Usage Data to Zuora standard.",
                "1.0",
                "Exclusive to Zuora standard",
                new Contact("Aditya Pant", "www.zuora.com", "apant@zuora.com"),
                "Zuora 2019",
                "https://www.zuora.com/terms-conditions/",
                Collections.emptyList()
        );
    }
}
