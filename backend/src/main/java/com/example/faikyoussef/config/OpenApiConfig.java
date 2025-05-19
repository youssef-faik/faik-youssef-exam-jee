package com.example.faikyoussef.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Banking System API")
                        .description("RESTful API for managing clients, credits, and repayments in a banking system")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Banking System Team")
                                .email("contact@bank-example.com")
                                .url("https://www.bank-example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Banking System Documentation")
                        .url("https://bank-example.com/docs"))
                .tags(Arrays.asList(
                        new Tag().name("Client Management").description("Operations about clients"),
                        new Tag().name("Credit Management").description("Operations about credits"),
                        new Tag().name("Remboursement Management").description("Operations about repayments"),
                        new Tag().name("Authentication").description("Operations for authentication and user management")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT Authorization header using the Bearer scheme. Example: \"Authorization: Bearer {token}\"")));
    }
}
