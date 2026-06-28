package com.consultorio.msnotificaciones.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.consultorio.msnotificaciones.dto.CitaDTO;

@FeignClient(name = "mscitas")
public interface CitasClient {

    @GetMapping("/api/citas")
    List<CitaDTO> listar();
}