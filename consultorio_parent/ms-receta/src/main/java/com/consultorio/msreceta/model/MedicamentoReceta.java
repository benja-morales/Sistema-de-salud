package com.consultorio.msreceta.model;

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
public class MedicamentoReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id del medicamento es obligatorio")
    @Column(nullable = false)
    private Long idMedicamento;

    @NotBlank(message = "El nombre del medicamento es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombreMedicamento;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    private Integer cantidad;

    @NotBlank(message = "Las indicaciones son obligatorias")
    @Column(nullable = false, length = 300)
    private String indicaciones;
}