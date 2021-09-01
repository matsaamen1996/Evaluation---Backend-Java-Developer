package com.is.evaluation.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .pathMapping("/")
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        Contact contact = new Contact("Evaluation", "http://www.mateosaavedramendoza.com",
                "mateomen1996@gmail.com.com");

        return new ApiInfo(
                "Evaluation - API",
                "EndPoints for Evaluation Ncoding",
                "0.1",
                "Terms of Service:",
                contact,
                "Mateo Fernando Saavedra Mendoza - Copyright 2021",
                "https://www.mateosaavedramendoza.com/",
                new ArrayList<>());
    }

    private static ArrayList<? extends SecurityScheme> securitySchemes() {

        return Lists.newArrayList(new ApiKey("Bearer", "Authorization", "header"));
    }
}
