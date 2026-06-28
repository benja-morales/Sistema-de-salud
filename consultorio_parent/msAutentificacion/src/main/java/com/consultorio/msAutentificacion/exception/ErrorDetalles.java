package com.consultorio.msAutentificacion.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetalles {
    private LocalDateTime timestamp;
    private String mensaje;
    private String detalles;
    private Map<String, String> erroresValidacion;
}