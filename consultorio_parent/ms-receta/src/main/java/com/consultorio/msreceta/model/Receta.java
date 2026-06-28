package com.consultorio.msreceta.model;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recetas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una receta médica")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID auto asignado de la receta")
    private Long id;

    @NotNull(message = "El id del paciente es obligatorio")
    @Column(nullable = false)
    @Schema(description = "ID del paciente asociado a la receta")
    private Long idPaciente;

    @NotNull(message = "El id del medico es obligatorio")
    @Column(nullable = false)
    @Schema(description = "ID del médico que emite la receta")
    private Long idMedico;

    @NotNull(message = "La fecha es obligatoria")
    @Column(nullable = false)
    @Schema(description = "Fecha de emisión de la receta")
    private LocalDate fecha;

    @NotEmpty(message = "La receta debe tener al menos un medicamento")
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "receta_id")
    @Schema(description = "Listado de medicamentos incluidos en la receta")
    private List<MedicamentoReceta> medicamentos;
}