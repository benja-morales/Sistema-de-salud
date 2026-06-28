package com.consultorio.ms_paciente.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa a un paciente del consultorio")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autoincremental del paciente", example = "1")
    private Long id;
    
    //Rut
    @NotBlank(message = "El RUT es obligatorio")
    @Column(unique = true, nullable = false, length = 12)
    @Schema(description = "RUT único del paciente (sin puntos ni dígito verificador)", example = "12345678")
    private String rut;

    //Dv
    @NotBlank(message = "El dígito verificador es obligatorio")
    @Column(nullable = false, length = 1)
    @Schema(description = "Dígito verificador del RUT", example = "9")
    private String dv;

    //Nombre
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Nombres del paciente", example = "Juan Carlos")
    private String nombre;

    //Apellido
    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(nullable = false)
    @Schema(description = "Apellidos (paterno y materno) del paciente", example = "Pérez Gómez")
    private String apellidos;

    //Email
    @Email(message = "Correo inválido")
    @Column(unique = true)
    @Schema(description = "Correo electrónico de contacto", example = "juan.perez@email.com")
    private String email;

    //Telefono
    @NotBlank(message = "El teléfono es obligatorio")
    @Column(length = 15)
    @Schema(description = "Número telefónico de contacto", example = "+56912345678")
    private String telefono;

    //Estado
    @Schema(description = "Estado de vigencia del paciente en el sistema (true: Activo, false: Inactivo)", example = "true")
    private Boolean activo;
}
