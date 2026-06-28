package consultorio.msAtencionMedica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import consultorio.msAtencionMedica.model.AtencionMedica;

@Repository
public interface AtencionMedicaRepository extends JpaRepository<AtencionMedica, Long> {
    Optional<AtencionMedica> findByIdCita(Long idCita);
}
