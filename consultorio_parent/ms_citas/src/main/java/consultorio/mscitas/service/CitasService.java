package consultorio.mscitas.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import consultorio.mscitas.client.MedicoClient;
import consultorio.mscitas.client.PacienteClient;
import consultorio.mscitas.model.Citas;
import consultorio.mscitas.repository.CitasRepository;

@Service
public class CitasService {

    @Autowired
    private CitasRepository repoCi;

    @Autowired
    private MedicoClient medicoClient;

    @Autowired
    private PacienteClient pacienteClient;

    public Citas crear(Citas cita) {

        // validar paciente
        try {
            pacienteClient.obtenerPaciente(cita.getIdPaciente());
        } catch (Exception e) {
            throw new RuntimeException("Paciente no existe");
        }

        // validar medico
        try {
            medicoClient.obtenerMedico(cita.getIdMedico());
        } catch (Exception e) {
            throw new RuntimeException("Medico no existe");
        }

        // Validar fecha
        validarFechas(cita);

        // validar disponibilidad
        boolean ocupado = repoCi.existsByIdMedicoAndFechaIniLessThanAndFechaTermGreaterThan(
                cita.getIdMedico(), cita.getFechaTerm(), cita.getFechaIni());

        if (ocupado) {
            throw new RuntimeException("El medico ya tiene una cita en ese horario");
        }

        cita.setEstado("PENDIENTE");
        return repoCi.save(cita);
    }

    public Citas reagendar(Long id, Citas nueva) {
        Citas cita = obtener(id);
        validarFechas(nueva);

        boolean ocupado = repoCi.existsByIdMedicoAndFechaIniLessThanAndFechaTermGreaterThan(
                nueva.getIdMedico(), nueva.getFechaTerm(), nueva.getFechaIni());

        if (ocupado) {
            throw new RuntimeException("Horario no disponible");
        }

        cita.setFechaIni(nueva.getFechaIni());
        cita.setFechaTerm(nueva.getFechaTerm());

        return repoCi.save(cita);
    }

    public Citas cancelar(Long id) {
        Citas cita = obtener(id);

        cita.setEstado("CANCELADA");

        return repoCi.save(cita);
    }

    public Citas cambiarEstado(Long id, String estado) {
        Citas cita = obtener(id);

        cita.setEstado(estado);
        if (!List.of("PENDIENTE", "CONFIRMADA").contains(estado)) {
            throw new RuntimeException("Estado inválido");
        }

        return repoCi.save(cita);
    }

    public Citas obtener(Long id) {
        return repoCi.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    public List<Citas> listar() {
        return repoCi.findAll();
    }

    public List<Citas> porPaciente(Long idPaciente) {
        return repoCi.findByIdPaciente(idPaciente);
    }

    public List<Citas> porMedico(Long idMedico) {
        return repoCi.findByIdMedico(idMedico);
    }

    public List<Citas> porFecha(LocalDate fecha) {
        return repoCi.findByFechaIniBetween(
                fecha.atStartOfDay(), fecha.atTime(23, 59, 59));
    }

    private void validarFechas(Citas cita) {
        if (cita.getFechaIni().isAfter(cita.getFechaTerm())) {
            throw new RuntimeException("La fecha de inicio no puede ser mayor que la de terminó");
        }
        if (cita.getFechaIni().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No puedes agendar en el pasado");
        }
    }

    // nuevas funciones
    public List<Citas> porEstado(String estado) {
        return repoCi.findByEstado(estado);
    }

    public List<Citas> pacientePorFecha(
            Long idPaciente,
            LocalDate fecha) {

        return repoCi.findByIdPacienteAndFechaIniBetween(
                idPaciente,
                fecha.atStartOfDay(),
                fecha.atTime(23, 59, 59));
    }

    public List<Citas> medicoPorFecha(
            Long idMedico,
            LocalDate fecha) {

        return repoCi.findByIdMedicoAndFechaIniBetween(
                idMedico,
                fecha.atStartOfDay(),
                fecha.atTime(23, 59, 59));
    }

    public Long totalPorMedico(Long idMedico) {
        return repoCi.countByIdMedico(idMedico);
    }

    public List<Citas> porRangoFechas(
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        return repoCi.findByFechaIniBetween(
                fechaInicio.atStartOfDay(),
                fechaFin.atTime(23, 59, 59));
    }
}
