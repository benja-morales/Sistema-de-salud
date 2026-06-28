package com.consultorio.msAutentificacion.modelo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "Roles de usuario permitidos en el sistema de salud",
    enumAsRef = true // Hace que en Swagger se cree un modelo global reutilizable llamado 'ERol'
)
public enum ERol {
    ROLE_ADMIN,
    ROLE_MEDICO,
    ROLE_PACIENTE
}
