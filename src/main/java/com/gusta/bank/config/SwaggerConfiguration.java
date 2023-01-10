package com.gusta.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String ALL_CHILD = ".*";

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("BankApi")
                .description("API reference guide")
                .contact(new Contact("", "", "gustavoangeli.silva@outlook"))
                .version("1.0")
                .build();
    }

    @Bean
    public Docket bankApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .select()
                .paths(regex("/api/bank/v1/" + ALL_CHILD))
                .build();

    }

}