package com.consultorio.msnotificaciones.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.consultorio.msnotificaciones.client.CitasClient;
import com.consultorio.msnotificaciones.client.ExamenesClient;
import com.consultorio.msnotificaciones.client.RecetasClient;
import com.consultorio.msnotificaciones.dto.CitaDTO;
import com.consultorio.msnotificaciones.dto.ExamenDTO;
import com.consultorio.msnotificaciones.dto.RecetaDTO;
import com.consultorio.msnotificaciones.model.Notificacion;
import com.consultorio.msnotificaciones.repository.NotificacionRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    @Autowired
    private CitasClient citasClient;

    @Autowired
    private RecetasClient recetasClient;

    @Autowired
    private ExamenesClient examenesClient;

    // LISTAR TODAS
    public List<Notificacion> listar() {

        return repository.findAll();
    }

    // BUSCAR POR ID
    public Notificacion buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Notificación no encontrada"));
    }

    // CREAR
    public Notificacion guardar(Notificacion notificacion) {

        return repository.save(notificacion);
    }

    // MARCAR COMO LEIDA
    public Notificacion marcarComoLeida(
            @NonNull Long id) {

        Notificacion notificacion =
                buscarPorId(id);

        notificacion.setLeida(true);

        return repository.save(notificacion);
    }

    // BUSCAR POR MODULO
    public List<Notificacion> buscarPorModulo(
            String modulo) {

        return repository.findByModulo(modulo);
    }

    // BUSCAR NO LEIDAS
    public List<Notificacion> buscarNoLeidas() {

        return repository.findByLeida(false);
    }

    // ELIMINAR
    public void eliminar(@NonNull Long id) {

        Notificacion notificacion =
                buscarPorId(id);

        repository.delete(notificacion);
    }

    // =========================
    // NOTIFICACIONES CITAS
    // =========================

    public List<String> obtenerNotificacionesCitas() {

        List<CitaDTO> citas =
                citasClient.listar();

        return citas.stream()
                .map(cita ->
                        "Cita del paciente "
                        + cita.getIdPaciente()
                        + " con el médico "
                        + cita.getIdMedico()
                        + " agendada para "
                        + cita.getFechaIni()
                        + " estado: "
                        + cita.getEstado())
                .collect(Collectors.toList());
    }

    // =========================
    // NOTIFICACIONES RECETAS
    // =========================

    public List<String> obtenerNotificacionesRecetas() {

        List<RecetaDTO> recetas =
                recetasClient.listar();

        return recetas.stream()
                .map(receta ->
                        "Receta creada para paciente "
                        + receta.getIdPaciente()
                        + " por médico "
                        + receta.getIdMedico()
                        + " en fecha "
                        + receta.getFecha())
                .collect(Collectors.toList());
    }

    // =========================
    // NOTIFICACIONES EXAMENES
    // =========================

    public List<String> obtenerNotificacionesExamenes() {

    List<ExamenDTO> examenes =
            examenesClient.listar();

    return examenes.stream()
            .map(examen ->
                    "Examen "
                    + examen.getTipoExamen()
                    + " del paciente "
                    + examen.getIdPaciente()
                    + " estado: "
                    + examen.getEstado()
                    + " solicitado el "
                    + examen.getFechaSolicitud())
            .toList();

    }
}