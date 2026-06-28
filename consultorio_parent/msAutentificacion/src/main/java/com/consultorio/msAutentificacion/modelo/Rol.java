package com.consultorio.msAutentificacion.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un rol dentro del sistema de salud")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    @Schema(description = "Identificador único y numérico del rol", example = "1")
    private Integer idRol;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    @Schema(
        description = "Nombre específico del rol basado en las opciones del catálogo ERol", 
        implementation = ERol.class // Vincula directamente con tu Enum de Swagger
    )
    private ERol nombre;
}
