package com.hiberus.hiring.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for the Product Pricing Service.
 * Sets the API title and version.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Product Pricing Service API")
                .version("1.0.0")
                .description("This API manages pricing offers for products using Hexagonal Architecture, applying SOLID principles, functional programming, Lombok, and JPA with PostgreSQL."));
    }
}