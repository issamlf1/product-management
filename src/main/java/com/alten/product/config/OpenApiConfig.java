package com.alten.product.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lanfouf
 **/
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product Management API",
                description = "This API manages products in the inventory system.",
                version = "1.0.0"
        )
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define the security scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization");

        // Attach the security scheme globally
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme));
    }
}


