package consultorio.mscitas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import consultorio.mscitas.model.Citas;

@Repository
public interface CitasRepository extends JpaRepository<Citas,Long>{
    
    List<Citas> findByIdPaciente(Long idPaciente);

    List<Citas> findByIdMedico(Long idMedico);

    List<Citas> findByFechaIniBetween(LocalDateTime inicio, LocalDateTime fin);

    boolean existsByIdMedicoAndFechaIniLessThanAndFechaTermGreaterThan(
        Long idMedico, LocalDateTime fechaTerm, LocalDateTime fechaIni);
}
