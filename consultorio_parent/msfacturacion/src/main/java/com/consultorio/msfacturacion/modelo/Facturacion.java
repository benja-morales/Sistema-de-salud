package com.consultorio.msfacturacion.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa la cabecera de una factura o cuenta médica en el sistema")
public class Facturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la factura", example = "105")
    private Long idFactura;

    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Identificador único del paciente asociado a la factura", example = "45")
    private Long idPaciente;

    @Column(nullable = false, updatable = false)
    @Schema(description = "Fecha y hora automática de emisión de la factura", example = "2026-06-10T15:45:00")
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    @Schema(description = "Monto total calculado automáticamente a partir de los detalles", example = "150.50")
    private Double total;

    @Schema(description = "Estado de pago actual de la factura (ej: PENDIENTE, PAGADA, ANULADA)", example = "PENDIENTE")
    private String estado; // Ejemplo: PAGADA, PENDIENTE, ANULADA

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista detallada de los ítems o servicios médicos cobrados en la factura")
    private List<FacturaDetalle> detalles = new ArrayList<>();

    //probar
    public void setDetalles(List<FacturaDetalle> nuevosDetalles) {
    if (this.detalles == null) {
        this.detalles = new ArrayList<>();
    }
    this.detalles.clear(); // Borra el contenido, no la lista
    if (nuevosDetalles != null) {
        this.detalles.addAll(nuevosDetalles); // Agrega el contenido nuevo
    }
}

    @PrePersist
    public void prePersist() {
        if (this.fechaEmision == null) {
            this.fechaEmision = LocalDateTime.now();
        }
        if (this.estado == null || this.estado.isBlank()) {
            this.estado = "PENDIENTE";
        }
        // Aseguramos que el total se calcule antes de guardar
        calcularTotal();
    }

    public void calcularTotal() {
        if (this.detalles != null) {
            this.total = detalles.stream()
                    .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                    .sum();
        } else {
            this.total = 0.0;
        }
    }

    
}

