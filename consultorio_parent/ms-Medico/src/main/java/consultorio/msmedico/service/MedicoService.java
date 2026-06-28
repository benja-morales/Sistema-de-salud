package consultorio.msmedico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import consultorio.msmedico.model.Medico;
import consultorio.msmedico.repository.MedicoRepository;

@Service
@Transactional
public class MedicoService {
    @Autowired
    private MedicoRepository repoMed;
    
    //Registrar
    public Medico registrar(Medico medico){
        if(repoMed.existsByRutMed(medico.getRutMed())){
            throw new RuntimeException("Error: EL rut ya existe");
        }
        // el exists del repository
        if(repoMed.existsByEmail(medico.getEmail())){
            throw new RuntimeException("Error: El email ya estaba registrado");
        }

        if(medico.getEspecialidad()==null || medico.getEspecialidad().isBlank()){
            throw new RuntimeException("Error: La especialidad es obligatoria");
        }

        return repoMed.save(medico);
    }

    //Actualizar
    public Medico actualizar(Long id,Medico medico){
        Medico medBD = repoMed.findById(id)
        .orElseThrow(() -> new RuntimeException("No se encontró el especialista"));

        medBD.setPnomMed(medico.getPnomMed());
        medBD.setSnomMed(medico.getSnomMed());
        medBD.setApePatMed(medico.getApePatMed());
        medBD.setApeMatMed(medico.getApeMatMed());
        medBD.setRutMed(medico.getRutMed());
        medBD.setDvMed(medico.getDvMed());
        medBD.setEspecialidad(medico.getEspecialidad());
        medBD.setEmail(medico.getEmail());
        medBD.setTelefono(medico.getTelefono());
        medBD.setFechaContratacion(medico.getFechaContratacion());
        medBD.setEstado(medico.getEstado());

        return repoMed.save(medBD);
    }

    //ELIMINAR (Lógico)
    public boolean eliminarLogic(Long id){
        Optional<Medico> medOpt = repoMed.findById(id);

        if(medOpt.isEmpty()){
            return false;
        }
        repoMed.delete(medOpt.get());
        return true;
    }
    
    //-------BUSQUEDAS -------
    
    //OBTENER POR ID 
    @Transactional(readOnly = true)
    public Optional<Medico> obtenerPorId(Long id){
        return repoMed.findById(id);
    }

    // LISTAR PERSONAL 
    @Transactional(readOnly = true)
    public List<Medico> listarTodo(){
        return repoMed.findAll();
    }

    // BUSCAR POR RUT
    @Transactional(readOnly = true)
    public Optional<Medico> buscarPorRut(Integer rut, String dv){
        return repoMed.findByRutMedAndDvMed(rut, dv);
    }
    //BUSCAR POR NOMBRE
    @Transactional(readOnly = true)
    public List<Medico> buscarPorNombre(String nombre){
        return repoMed.findByPnomMedContainingIgnoreCaseOrSnomMedContainingIgnoreCase(nombre,nombre);
    }
    
    //BUSCAR POR ESPECIALIDAD
    @Transactional(readOnly = true)
    public List<Medico> buscarPorEspecialidad(String especialidad){
        return repoMed.findByEspecialidadContainingIgnoreCase(especialidad);
    }

}