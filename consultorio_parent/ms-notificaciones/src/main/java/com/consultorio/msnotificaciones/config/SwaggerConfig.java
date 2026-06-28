package com.consultorio.msnotificaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI custOpenApi() {
        return new OpenAPI()
                .info(new Info()
                    .title("API Consultorio - Notificaciones 2026")
                    .version("1.0")
                    .description("Documentación de la API para el sistema de registro de cambios o notificaciones"));
    }
}
