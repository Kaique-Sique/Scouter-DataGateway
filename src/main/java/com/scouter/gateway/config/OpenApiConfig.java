package com.scouter.gateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI scouterOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Scouter DataGateway API")
                .version("0.0.1")
                .description("Internal scouting API — FRC Team 7563 Megazord"))
            .addSecurityItem(new SecurityRequirement().addList("X-Credentials"))
            .components(new Components()
                .addSecuritySchemes("X-Credentials",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("X-Credentials")
                        .description("Format: email@example.com/yourpassword")));
    }
}