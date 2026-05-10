package com.soumya.urlshortner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(
                                        "URL shortner API"
                                )
                                .version("1.0")
                        .description(
                              "Url shortner built using Spring Boot, Redis, Rate Limiting, Scheduling, and Mysql"
                        )
                );
    }
}
