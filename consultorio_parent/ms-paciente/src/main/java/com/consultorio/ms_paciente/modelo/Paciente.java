package com.consultorio.ms_paciente.modelo;

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
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //Rut
    @NotBlank(message = "El RUT es obligatorio")
    @Column(unique = true, nullable = false, length = 12)
    private String rut;

    //Dv
    @NotBlank(message = "El dígito verificador es obligatorio")
    @Column(nullable = false, length = 1)
    private String dv;

    //Nombre
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    //Apellido
    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(nullable = false)
    private String apellidos;

    //Email
    @Email(message = "Correo inválido")
    @Column(unique = true)
    private String email;

    //Telefono
    @NotBlank(message = "El teléfono es obligatorio")
    @Column(length = 15)
    private String telefono;

    //Estado
    private Boolean activo;
}
