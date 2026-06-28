package com.consultorio.msnotificaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class FeignConfig {

    @SuppressWarnings("unused")
    @Bean
    Logger.Level feignLoggerLevel() {

        return Logger.Level.FULL;
    }
}