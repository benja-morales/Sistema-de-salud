package com.consultorio.msfacturacion.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-atencion-medica")
public interface AtencionClient {

    @GetMapping("/api/atenciones/{id}")
    Object obtenerAtencionPorId(@PathVariable("id") Long id);
}
