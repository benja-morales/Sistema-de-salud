package consultorio.msAtencionMedica.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import consultorio.msAtencionMedica.client.CitasClient;
import consultorio.msAtencionMedica.model.AtencionMedica;
import consultorio.msAtencionMedica.model.Cita;
import consultorio.msAtencionMedica.repository.AtencionMedicaRepository;

@Service
public class AtencionMedicaService {

    @Autowired
    private AtencionMedicaRepository repo;

    @Autowired
    private CitasClient citasClient;

    // registar atencion
    public AtencionMedica registrar(AtencionMedica atencion) {

        Cita cita;

        // validar que la cita existe
        try {
            cita = citasClient.obtenerCita(atencion.getIdCita());
        } catch (Exception e) {
            throw new RuntimeException("La cita no existe");
        }

        if (!"PENDIENTE".equalsIgnoreCase(cita.getEstado())) {
            throw new RuntimeException("La cita no esta en estado PENDIENTE");
        }

        // validar que no exista atencion para esa cita
        repo.findByIdCita(atencion.getIdCita()).ifPresent(a -> {
            throw new RuntimeException("La cita ya tiene una atencion registrada");
        });

        atencion.setFechaAtencion(LocalDateTime.now());

        return repo.save(atencion);
    }

    // LLamar a todas las atenciones
    public List<AtencionMedica> listar() {
        return repo.findAll();
    }

    // obtener por id
    public AtencionMedica obtener(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new RuntimeException("Atención no encontrada"));
    }

    // asociar a Cita
    public AtencionMedica asociarACita(Long idCita, AtencionMedica atencion) {
        atencion.setIdCita(idCita);
        return registrar(atencion);
    }

    // registrar diagnostico PUT
    public AtencionMedica agregarDiagnostico(Long id, String diagnostico) {
        if (diagnostico == null || diagnostico.isBlank()) {
            throw new RuntimeException("El diagnóstico no puede estar vació");
        }
        AtencionMedica a = obtener(id);
        a.setDiagnostico(diagnostico);

        return repo.save(a);
    }

    // Registrar Observaciones PUT
    public AtencionMedica agregarobservaciones(Long id, String observaciones) {
        if (observaciones == null || observaciones.isBlank()) {
            throw new RuntimeException("Las observaciones no pueden estar vacías");
        }
        AtencionMedica a = obtener(id);
        a.setObservaciones(observaciones);

        return repo.save(a);
    }

}
