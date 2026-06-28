package com.consultorio.msfacturacion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de validación (@Valid, @NotNull, @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetalles> manejarValidaciones(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errores = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        ErrorDetalles errorDetalles = new ErrorDetalles(
                LocalDateTime.now(),
                "Error de validación en los campos",
                request.getDescription(false),
                errores
        );

        return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
    }

    // Maneja errores genéricos (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarTodasLasExcepciones(Exception ex, WebRequest request) {
        ErrorDetalles errorDetalles = new ErrorDetalles(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                null
        );
        return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
