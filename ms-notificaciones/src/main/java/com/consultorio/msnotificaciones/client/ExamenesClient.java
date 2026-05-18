package com.consultorio.msnotificaciones.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.consultorio.msnotificaciones.dto.ExamenDTO;

@FeignClient(name = "ms-examenes")
public interface ExamenesClient {

    @GetMapping("/api/examenes")
    List<ExamenDTO> listar();
}