package com.consultorio.msnotificaciones.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entidad que representa una notificación generada por el sistema")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID auto asignado de la notificación")
    private Long id;

    @NotBlank(message = "El módulo es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Módulo que genera la notificación, por ejemplo citas, recetas o exámenes")
    private String modulo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Contenido de la notificación")
    private String mensaje;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora en que se generó la notificación")
    private LocalDateTime fecha;

    @Column(nullable = false)
    @Schema(description = "Indica si la notificación fue leída")
    private Boolean leida;

    @PrePersist
    public void prePersist() {

        if (fecha == null) {
            fecha = LocalDateTime.now();
        }

        if (leida == null) {
            leida = false;
        }
    }
}