package com.consultorio.msfacturacion.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

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
public class Facturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    // Relación lógica con el ID del paciente (como en tu modelo de Examen)
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(nullable = false)
    private Long idPaciente;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    private Double total;

    private String estado; // Ejemplo: PAGADA, PENDIENTE, ANULADA

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
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

    @SpringBootApplication
    @EnableFeignClients // <--- Indispensable para que funcione la carpeta client
    public class MsFacturacionApplication {
        public static void main(String[] args) {
            SpringApplication.run(MsFacturacionApplication.class, args);
        }
    }
}

