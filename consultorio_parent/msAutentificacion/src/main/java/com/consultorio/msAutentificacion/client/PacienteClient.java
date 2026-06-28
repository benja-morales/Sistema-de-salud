package com.consultorio.msAutentificacion.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-paciente")
public interface PacienteClient {

    @GetMapping("/api/pacientes/rut/{rut}")
    Object buscarPacientePorRut(@PathVariable("rut") String rut);
}