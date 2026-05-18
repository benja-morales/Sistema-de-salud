package com.consultorio.msnotificaciones.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.consultorio.msnotificaciones.dto.RecetaDTO;

@FeignClient(name = "msreceta")
public interface RecetasClient {

    @GetMapping("/api/recetas")
    List<RecetaDTO> listar();
}