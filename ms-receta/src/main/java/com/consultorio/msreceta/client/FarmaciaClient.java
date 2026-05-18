package com.consultorio.msreceta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.consultorio.msreceta.dto.MedicamentoDTO;

@FeignClient(name = "msfarmacia")
public interface FarmaciaClient {

    @GetMapping("/api/medicamentos/{id}")
    MedicamentoDTO obtenerMedicamento(
            @PathVariable Long id);
}