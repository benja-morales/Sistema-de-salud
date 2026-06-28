package consultorio.mscitas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import consultorio.mscitas.model.Citas;

@Repository
public interface CitasRepository extends JpaRepository<Citas, Long> {

    List<Citas> findByIdPaciente(Long idPaciente);

    List<Citas> findByIdMedico(Long idMedico);

    List<Citas> findByFechaIniBetween(LocalDateTime inicio, LocalDateTime fin);

    boolean existsByIdMedicoAndFechaIniLessThanAndFechaTermGreaterThan(
            Long idMedico, LocalDateTime fechaTerm, LocalDateTime fechaIni);

    // nuevas funciones
    List<Citas> findByEstado(String estado);

    List<Citas> findByIdPacienteAndFechaIniBetween(
            Long idPaciente,
            LocalDateTime inicio,
            LocalDateTime fin);

    List<Citas> findByIdMedicoAndFechaIniBetween(
            Long idMedico,
            LocalDateTime inicio,
            LocalDateTime fin);

    Long countByIdMedico(Long idMedico);

}
