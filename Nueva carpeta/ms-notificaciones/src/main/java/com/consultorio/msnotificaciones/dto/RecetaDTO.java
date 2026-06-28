package com.consultorio.msnotificaciones.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaDTO {

    private Long id;

    private Long idPaciente;

    private Long idMedico;

    private LocalDate fecha;

}