package com.consultorio.msreceta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.consultorio.msreceta.dto.PacienteDTO;

@FeignClient(name = "mspaciente")
public interface PacienteClient {

    @GetMapping("/api/pacientes/{id}")
    PacienteDTO obtenerPaciente(
            @PathVariable Long id);
}