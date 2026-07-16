package com.adarsh.quickqr.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger configuration.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI quickQROpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("QuickQR API")
                        .description("REST API for generating, managing, and downloading QR Codes")
                        .version("1.0.0"));
    }
}
