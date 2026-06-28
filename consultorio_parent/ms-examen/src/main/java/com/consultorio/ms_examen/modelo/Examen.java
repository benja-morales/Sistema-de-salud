package com.consultorio.ms_examen.modelo;

import java.time.LocalDate;
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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "examenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una orden de examen médico")
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la orden de examen", example = "1")
    private Long idExamen;

    @NotBlank(message = "El tipo de examen es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Nombre o tipo de examen clínico a realizar (ej: Hemograma, Orina, Rayos X)", example = "Hemograma Completo")
    private String tipoExamen;   

    // Relación lógica con el microservicio de Pacientes
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Identificador único del paciente asociado al examen", example = "45")
    private Long idPaciente;


    @Column(nullable = false, updatable = false)
    @Schema(description = "Fecha y hora automática en la que se generó la solicitud del examen", example = "2026-06-10T15:30:00")
    private LocalDateTime fechaSolicitud;

    // Puede ser null al inicio hasta que el examen se procese
    private LocalDate fechaResultado;

    @Column(columnDefinition = "TEXT") //informes largos
    @Schema(description = "Fecha en la que se entregaron u obtuvieron los resultados finales", example = "2026-06-12")
    private String resultadoTexto;

    @Column(columnDefinition = "TEXT") // informes largos
    @Schema(description = "Detalle de las observaciones o conclusiones médicas del examen", example = "Niveles de glóbulos rojos y hemoglobina dentro de los rangos normales.")
    private String archivoUrl;

    // Ejemplo: PENDIENTE, EN_PROCESO, COMPLETADO, CANCELADO
    @Schema(description = "Estado actual del flujo del examen (ej: PENDIENTE, EN_PROCESO, COMPLETADO)", example = "PENDIENTE")
    private String estado;

    @PrePersist
    public void prePersist() {

    if (this.fechaSolicitud == null) {
        this.fechaSolicitud = LocalDateTime.now();
    }

    if (this.estado == null || this.estado.isBlank()) {
        this.estado = "PENDIENTE";
    }
    }

    

}
