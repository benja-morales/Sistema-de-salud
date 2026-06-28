package com.consultorio.msfarmacia.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description= "Entidad que representa un medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description= "ID auto asignado del medicamento")
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description= "nombre del medicamento")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "La descripcion es obligatoria")
    @Schema(description= "descripcion del medicamento, ej: antiflamatorio")
    private String descripcion;

    @Column(nullable = false)
    @NotNull(message = "El stock no puede ser menor a 0 o palabra")
    @Schema(description= "Cantidad actual del medicamento presente en el inventario")
    private Integer stock;

    @Column(nullable = false)
    @NotNull(message = "El precio no puede ser menos a 0 o palabra")
    @Schema(description= "Valor monetario referente al medicamento")
    private Double precio;

    @Column(nullable = false)
    @NotBlank(message = "El laboratorio es obligatorio")
    @Schema(description= "nombre del laboratio elaborador del medicamento")
    private String laboratorio;

}