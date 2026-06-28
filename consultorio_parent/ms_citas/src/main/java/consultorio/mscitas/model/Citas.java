package consultorio.mscitas.model;

import java.time.LocalDateTime;

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
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Citas")
public class Citas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    @NotNull(message = "El paciente es obligatorio")
    private Long idPaciente;

    @NotNull(message = "El especialista es obligatorio")
    private Long idMedico;

    @NotNull(message = "La fecha de inicio de la cita es obligatoria")
    @Column(name = "fech_ini", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaIni;

    @NotNull(message = "La fecha de termino de cita es obligatoria")
    @Column(name = "fech_term", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaTerm;

    @NotBlank(message = "El estado es obligatorio")
    @Column(name = "estado", nullable = false)
    private String estado;
}
