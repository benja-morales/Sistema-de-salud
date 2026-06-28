package consultorio.msAtencionMedica.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atencion_medica")
@Schema(description = "Entidad que representa una atención médica realizada a un paciente")
public class AtencionMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la atención médica", example = "1")
    private Long idAtencion;

    @NotNull(message = "La cita es obligatorio")
    @Schema(description = "ID de la cita asociada a la atención médica", example = "8")
    private Long idCita;

    @Column(name = "diagnos", nullable = false, length = 100)
    @Schema(description = "Diagnóstico emitido durante la atención", example = "Dolor agudo en pie izquierdo")
    private String diagnostico;

    @Column(name = "observ", nullable = false, length = 2000)
    @Schema(description = "Observaciones registradas por el médico", example = "Paciente presenta inflamación y dificultad para caminar")
    private String observaciones;

    @NotNull(message = "La fecha de atencion es obligatorio")
    @Column(name = "fecha_aten", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(description = "Fecha y hora en que se realizó la atención médica", example = "21-06-2026 10:30:00")
    private LocalDateTime fechaAtencion;
}
