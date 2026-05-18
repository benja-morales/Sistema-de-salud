package com.consultorio.msnotificaciones.exception;

public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}