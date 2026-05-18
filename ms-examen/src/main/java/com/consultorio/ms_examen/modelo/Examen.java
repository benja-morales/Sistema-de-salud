package com.consultorio.ms_examen.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

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
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExamen;

    @NotBlank(message = "El tipo de examen es obligatorio")
    @Column(nullable = false)
    private String tipoExamen;   

    // Relación lógica con el microservicio de Pacientes
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(nullable = false)
    private Long idPaciente;


    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaSolicitud;

    // Puede ser null al inicio hasta que el examen se procese
    private LocalDate fechaResultado;

    @Column(columnDefinition = "TEXT") //informes largos
    private String resultadoTexto;

    private String archivoUrl;

    // Ejemplo: PENDIENTE, EN_PROCESO, COMPLETADO, CANCELADO
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

    @SpringBootApplication
    @EnableFeignClients // <--- OBLIGATORIO para que Spring busque e inyecte los clientes
    public class TuMicroservicioApplication {
    public static void main(String[] args) {
        SpringApplication.run(TuMicroservicioApplication.class, args);
    }
    }

}
