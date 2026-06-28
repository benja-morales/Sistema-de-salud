package com.consultorio.msnotificaciones.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamenDTO {

    private Long idExamen;

    private String tipoExamen;

    private Long idPaciente;

    private LocalDateTime fechaSolicitud;

    private LocalDate fechaResultado;

    private String resultadoTexto;

    private String archivoUrl;

    private String estado;
}