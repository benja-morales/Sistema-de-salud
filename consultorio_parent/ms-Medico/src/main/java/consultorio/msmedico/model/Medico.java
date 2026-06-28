package consultorio.msmedico.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="Medico")
@Schema(description = "Entidad que representa un Medico")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del médico", example = "1")
    private Long idMed;

    @NotBlank(message = "El primer nombre es obligatorio")
    @Column(name = "pnom_med",nullable = false,length = 20)
    @Schema(description = "Primer nombre médico", example = "Juan")
    private String pnomMed;
    
    @Column(name = "snom_med",length = 20)
    @Schema(description = "Segundo nombre del médico",example = "Carlos")
    private String snomMed;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Column(name = "ape_pat_med", nullable = false,length = 20)
    @Schema(description = "Primer apellido del médico",example = "Pérez")
    private String apePatMed;
    
    @NotBlank(message = "El apellido materno es obligatorio")
    @Column(name = "ape_mat_med",nullable = false,length = 20)
    @Schema(description = "Segundo apellido del médico",example = "González")
    private String apeMatMed;

    @NotNull(message = "El run es obligatorio")
    @Column(name = "rut_med", unique = true, nullable = false)
    @Schema(description = "RUN del médico sin dígito verificador",example = "11222333")
    private Integer rutMed;

    @NotBlank(message = "El rutificador es obligatorio")
    @Column(name = "dv_med", nullable = false,length = 1)
    @Schema(description = "Dígito verificador del RUN",example = "k")
    private String dvMed;
    
    @NotBlank(message = "La especialidad es obligatorio")
    @Column(nullable = false,length = 30)
    @Schema(description = "Especialidad médica",example = "Cardiología")
    private String especialidad;
    
    @Email(message = "Debe ingresar un correo valido")
    @NotBlank(message = "El Correo es obligatorio")
    @Column(nullable = false, unique = true)
    @Schema(description = "Correo electrónico institucional del médico",example = "juan.perez@consultorio.cl")
    private String email;
    
    @NotBlank(message = "El n° de telefono es obligatorio")
    @Column(nullable = false, length = 15)
    @Schema(description = "Número de teléfono de contacto",example = "+56977884422")
    private String telefono;
    
    @NotNull(message = "La fecha de contratacion es obligatorio")
    @Column(name = "fecha_contratacion", nullable = false)
    @Schema(description = "Fecha en que el médico fue contratado",example = "2026-01-15")
    private LocalDate fechaContratacion;

    @NotNull(message = "El estado es obligatorio")
    @Column(name = "estado", nullable = false)
    @Schema(description = "Estado del médico: true = activo,", example = "true")
    private Boolean estado;
}
