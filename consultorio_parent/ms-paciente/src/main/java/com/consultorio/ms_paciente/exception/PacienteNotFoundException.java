package com.consultorio.ms_paciente.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Es para la excepcion, el servidor responda con un 404 Not Found
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PacienteNotFoundException extends RuntimeException {

    // Constructor que acepta solo el mensaje
    public PacienteNotFoundException(String mensaje) {
        super(mensaje);
    }

}