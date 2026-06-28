package com.consultorio.msfacturacion.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "factura_detalles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa el desglose de un ítem, servicio o concepto específico cobrado dentro de una factura")
public class FacturaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la línea de detalle", example = "1")
    private Long idDetalle;

    @NotBlank(message = "La descripción del concepto es obligatoria")
    @Column(nullable = false)
    @Schema(description = "Nombre del servicio médico, consulta, insumo o examen cobrado", example = "Consulta Médica General")
    private String concepto;

    @NotNull(message = "El precio unitario es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Costo individual del servicio o concepto (sin IVA/descuentos aplicados directamente aquí)", example = "75.25")
    private Double precioUnitario;

    @NotNull(message = "La cantidad es obligatoria")
    @Column(nullable = false)
    @Schema(description = "Número de veces que se suministró o aplicó el servicio o concepto", example = "2")
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura")
    @JsonIgnore // Para evitar bucles infinitos al convertir a JSON
    @Schema(description = "Referencia a la factura cabecera a la cual pertenece este detalle", hidden = true)
    private Facturacion factura;

}