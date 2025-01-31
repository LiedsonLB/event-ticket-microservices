package com.msticket.ms_ticket_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocOpenApiConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
            new Info()
                .title("ms-ticket-management API")
                .description("API for managing tickets")
                .version("v1.0.0")
        );
    }
}
