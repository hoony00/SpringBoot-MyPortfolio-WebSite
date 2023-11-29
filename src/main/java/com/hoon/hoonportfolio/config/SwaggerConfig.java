package com.hoon.hoonportfolio.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    // localhost:xxxx/swagger-ui/index.html#/  << 접속경로
    public OpenAPI baseOpenAPI() {
        return new OpenAPI().
                addSecurityItem(
                        new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearerAuth", "read write")
                ).path("/**", new PathItem()).
                info(new Info()
                        .title("포토폴리오관리사이트")
                        .version("1.0.0")
                        .description("스프링 과제"));
    }

}
