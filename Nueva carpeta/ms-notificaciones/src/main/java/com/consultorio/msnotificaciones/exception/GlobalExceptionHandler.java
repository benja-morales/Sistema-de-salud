package com.consultorio.msnotificaciones.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // VALIDACIONES
    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errores.put(
                                error.getField(),
                                error.getDefaultMessage()));

        Map<String, Object> body =
                new HashMap<>();

        body.put("mensaje",
                "Error de validación");

        body.put("errores", errores);

        body.put("timestamp",
                LocalDateTime.now());

        body.put("status", 400);

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    // NOT FOUND
    @ExceptionHandler(
            ResourceNotFoundException.class)
    public ResponseEntity<?> manejarNotFound(
            ResourceNotFoundException ex) {

        Map<String, Object> body =
                new HashMap<>();

        body.put("mensaje",
                ex.getMessage());

        body.put("timestamp",
                LocalDateTime.now());

        body.put("status", 404);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    // GENERALES
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejarGeneral(
            Exception ex) {

        Map<String, Object> body =
                new HashMap<>();

        body.put("mensaje",
                "Ocurrió un error inesperado");

        body.put("detalle",
                ex.getMessage());

        body.put("timestamp",
                LocalDateTime.now());

        body.put("status", 500);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}