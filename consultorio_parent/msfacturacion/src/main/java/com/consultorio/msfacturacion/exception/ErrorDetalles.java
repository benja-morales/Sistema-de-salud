package com.consultorio.msfacturacion.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorDetalles {
    private LocalDateTime timestamp;
    private String mensaje;
    private String detalles;
    private Map<String, String> erroresValidacion; // Para capturar errores de @Valid
}

//Este apoya al global exception
