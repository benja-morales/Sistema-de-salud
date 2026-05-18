package com.consultorio.msfacturacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 'ms-paciente' debe ser el nombre definido en el properties del microservicio de pacientes
@FeignClient(name = "ms-paciente")
public interface PacienteClient {

    @GetMapping("/api/pacientes/{id}")
    Object obtenerPacientePorId(@PathVariable("id") Long id);
}