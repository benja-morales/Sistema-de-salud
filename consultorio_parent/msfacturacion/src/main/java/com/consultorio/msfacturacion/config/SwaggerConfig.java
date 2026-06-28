package com.consultorio.msfacturacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean 
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API Consultorio - Modulo Factura")
                        .version("1.1")
                        .description("Documentacion de la API para el sistema de reservas de examanes"));
    }

}

//fiiiiiiii