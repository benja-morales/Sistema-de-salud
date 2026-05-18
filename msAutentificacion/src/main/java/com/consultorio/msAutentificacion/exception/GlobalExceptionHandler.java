package com.consultorio.msAutentificacion.exception;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Error 401: Cuando el Login falla (Usuario o contraseña incorrectos)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetalles> manejarCredencialesInvalidas(BadCredentialsException ex, WebRequest request) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                "Credenciales de acceso incorrectas",
                request.getDescription(false),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Error 400: Errores de validación (Email mal escrito, campos vacíos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetalles> manejarValidaciones(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errores.put(err.getField(), err.getDefaultMessage());
        });

        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                "Error en los datos enviados",
                request.getDescription(false),
                errores
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Error 400: Usuario o Email ya duplicados (Lanzados desde nuestro Service)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetalles> manejarRuntime(RuntimeException ex, WebRequest request) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}