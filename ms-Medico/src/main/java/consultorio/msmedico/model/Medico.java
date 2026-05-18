package consultorio.msmedico.model;

import java.time.LocalDate;

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
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMed;

    @NotBlank(message = "El primer nombre es obligatorio")
    @Column(name = "pnom_med",nullable = false,length = 20)
    private String pnomMed;
    
    @Column(name = "snom_med",length = 20)
    private String snomMed;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Column(name = "ape_pat_med", nullable = false,length = 20)
    private String apePatMed;
    
    @NotBlank(message = "El apellido materno es obligatorio")
    @Column(name = "ape_mat_med",nullable = false,length = 20)
    private String apeMatMed;

    @NotNull(message = "El run es obligatorio")
    @Column(name = "rut_med", unique = true, nullable = false)
    private Integer rutMed;

    @NotBlank(message = "El rutificador es obligatorio")
    @Column(name = "dv_med", nullable = false,length = 1)
    private String dvMed;
    
    @NotBlank(message = "La especialidad es obligatorio")
    @Column(nullable = false,length = 30)
    private String especialidad;
    
    @Email(message = "Debe ingresar un correo valido")
    @NotBlank(message = "El Correo es obligatorio")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "El n° de telefono es obligatorio")
    @Column(nullable = false, length = 15)
    private String telefono;
    
    @NotNull(message = "La fecha de contratacion es obligatorio")
    @Column(name = "fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;

    @NotNull(message = "El estado es obligatorio")
    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
