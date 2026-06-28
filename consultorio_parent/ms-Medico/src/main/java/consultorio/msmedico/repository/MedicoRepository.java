package consultorio.msmedico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import consultorio.msmedico.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByIdMed (Long idMed);
    
    boolean existsByRutMed(Integer rutMed);
    
    boolean existsByEmail(String email);
    // se supone que  debe llevar una "s" al exist, seria exists 
    List<Medico> findByEspecialidadContainingIgnoreCase(String especialidad);

    Optional<Medico> findByRutMedAndDvMed(Integer rut, String dv);

    List<Medico> findByPnomMedContainingIgnoreCaseOrSnomMedContainingIgnoreCase(String nombre, String nombre2);
    
}
