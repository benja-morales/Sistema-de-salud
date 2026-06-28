package com.consultorio.msreceta.dto;

import lombok.Data;

@Data
public class MedicamentoDTO {

    private Long id;
    private String nombre;
    private Integer stock;
}