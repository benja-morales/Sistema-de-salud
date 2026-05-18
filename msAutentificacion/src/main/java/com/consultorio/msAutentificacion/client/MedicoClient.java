package com.consultorio.msAutentificacion.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-medico")
public interface MedicoClient {

    @GetMapping("/api/medicos/usuario/{username}")
    Object buscarMedicoPorUsuario(@PathVariable("username") String username);
}