package com.consultorio.msfarmacia.model;

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
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @Column(nullable = false)
    @NotNull(message = "El stock no puede ser menor a 0 o palabra")
    private Integer stock;

    @Column(nullable = false)
    @NotNull(message = "El precio no puede ser menos a 0 o palabra")
    private Double precio;

    @Column(nullable = false)
    @NotBlank(message = "El laboratorio es obligatorio")
    private String laboratorio;

}