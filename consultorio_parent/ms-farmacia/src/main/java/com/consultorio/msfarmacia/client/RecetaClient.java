package com.consultorio.msfarmacia.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-recetas")
public interface RecetaClient {

    @GetMapping("/api/v1/recetas/{id}")
    Map<String, Object> obtenerReceta(
            @PathVariable Long id);
}