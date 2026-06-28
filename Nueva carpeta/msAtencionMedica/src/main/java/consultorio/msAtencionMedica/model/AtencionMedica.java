package consultorio.msAtencionMedica.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class AtencionMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtencion;

    @NotNull(message = "La cita es obligatorio")
    private Long idCita;

    @Column(name = "diagnos", nullable = false, length = 100)
    private String diagnostico;

    @Column(name = "observ", nullable = false, length = 2000)
    private String observaciones;

    @NotNull(message = "La fecha de atencion es obligatorio")
    @Column(name = "fecha_aten", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaAtencion;
}
