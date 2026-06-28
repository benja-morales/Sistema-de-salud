package com.consultorio.msreceta.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicamentos_receta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Medicamento incluido dentro de una receta médica")
public class MedicamentoReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID auto asignado del medicamento en receta")
    private Long id;

    @NotNull(message = "El id del medicamento es obligatorio")
    @Column(nullable = false)
    @Schema(description = "ID del medicamento registrado en farmacia")
    private Long idMedicamento;

    @NotBlank(message = "El nombre del medicamento es obligatorio")
    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre del medicamento")
    private String nombreMedicamento;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    @Schema(description = "Cantidad prescrita del medicamento")
    private Integer cantidad;

    @NotBlank(message = "Las indicaciones son obligatorias")
    @Column(nullable = false, length = 300)
    @Schema(description = "Indicaciones de uso del medicamento")
    private String indicaciones;
}