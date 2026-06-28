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

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Citas")
@Schema
public class Citas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "Identificaro único de la cita",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idCita;

    @NotNull(message = "El paciente es obligatorio")
    @Schema(
        description = "Id del paciente asociado a la cita",
        example = "10",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long idPaciente;

    @NotNull(message = "El especialista es obligatorio")
    @Schema(
        description = "Id del médico asociado a la cita",
        example = "5",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long idMedico;

    @NotNull(message = "La fecha de inicio de la cita es obligatoria")
    @Column(name = "fech_ini", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(
        description = "Fecha y hora de inicio de la cita",
        example = "21-06-2026 10:00:00"
    )
    private LocalDateTime fechaIni;

    @NotNull(message = "La fecha de termino de cita es obligatoria")
    @Column(name = "fech_term", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(
        description = "Fecha y hora de termino de la cita",
        example = "21-06-2026 10:30:00"
    )
    private LocalDateTime fechaTerm;

    @NotBlank(message = "El estado es obligatorio")
    @Column(name = "estado", nullable = false)
    @Schema(
        description = "Estado actual de la cita",
        example = "PROGRAMADA"
    )
    private String estado;
}
