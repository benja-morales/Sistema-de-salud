package com.consultorio.msnotificaciones.model;

import java.time.LocalDateTime;

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
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El módulo es obligatorio")
    @Column(nullable = false)
    private String modulo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
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