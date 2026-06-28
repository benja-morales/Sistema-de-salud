package com.consultorio.msfarmacia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI farmaciaOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("MS Farmacia API")
                        .description("Microservicio encargado de la gestión de medicamentos y farmacia")
                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Consultorio Microservices Team")
                                .email("equipo@consultorio.cl"))

                        .license(new License()
                                .name("Uso académico")))

                .externalDocs(new ExternalDocumentation()
                        .description("Documentación del proyecto")
                        .url("https://github.com"));
    }
}