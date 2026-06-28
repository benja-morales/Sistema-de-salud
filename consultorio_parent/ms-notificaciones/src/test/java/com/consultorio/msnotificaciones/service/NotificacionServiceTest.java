package com.consultorio.msnotificaciones.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.consultorio.msnotificaciones.client.CitasClient;
import com.consultorio.msnotificaciones.client.ExamenesClient;
import com.consultorio.msnotificaciones.client.RecetasClient;
import com.consultorio.msnotificaciones.dto.CitaDTO;
import com.consultorio.msnotificaciones.dto.ExamenDTO;
import com.consultorio.msnotificaciones.dto.RecetaDTO;
import com.consultorio.msnotificaciones.model.Notificacion;
import com.consultorio.msnotificaciones.repository.NotificacionRepository;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository;

    @Mock
    private CitasClient citasClient;

    @Mock
    private RecetasClient recetasClient;

    @Mock
    private ExamenesClient examenesClient;

    @InjectMocks
    private NotificacionService service;

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {

        notificacion = Notificacion.builder()
                .id(1L)
                .modulo("CITAS")
                .mensaje("Cita agendada")
                .leida(false)
                .fecha(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Debe listar notificaciones")
    void debeListarNotificaciones() {

        when(repository.findAll())
                .thenReturn(List.of(notificacion));

        List<Notificacion> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Debe buscar notificacion por id")
    void debeBuscarPorId() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(notificacion));

        Notificacion resultado = service.buscarPorId(1L);

        assertEquals(1L, resultado.getId());

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepcion cuando no existe")
    void debeLanzarExcepcionAlBuscarId() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.buscarPorId(1L));

        assertEquals(
                "Notificación no encontrada",
                ex.getMessage());

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Debe guardar notificacion")
    void debeGuardarNotificacion() {

        when(repository.save(notificacion))
                .thenReturn(notificacion);

        Notificacion resultado =
                service.guardar(notificacion);

        assertEquals("CITAS",
                resultado.getModulo());

        verify(repository).save(notificacion);
    }

    @Test
    @DisplayName("Debe marcar notificacion como leida")
    void debeMarcarComoLeida() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(notificacion));

        when(repository.save(notificacion))
                .thenReturn(notificacion);

        Notificacion resultado =
                service.marcarComoLeida(1L);

        assertEquals(true,
                resultado.getLeida());

        verify(repository).findById(1L);
        verify(repository).save(notificacion);
    }

    @Test
    @DisplayName("Debe buscar por modulo")
    void debeBuscarPorModulo() {

        when(repository.findByModulo("CITAS"))
                .thenReturn(List.of(notificacion));

        List<Notificacion> resultado =
                service.buscarPorModulo("CITAS");

        assertEquals(1, resultado.size());

        verify(repository).findByModulo("CITAS");
    }

    @Test
    @DisplayName("Debe buscar no leidas")
    void debeBuscarNoLeidas() {

        when(repository.findByLeida(false))
                .thenReturn(List.of(notificacion));

        List<Notificacion> resultado =
                service.buscarNoLeidas();

        assertFalse(resultado.isEmpty());

        verify(repository).findByLeida(false);
    }

    @Test
    @DisplayName("Debe eliminar notificacion")
    void debeEliminarNotificacion() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(notificacion));

        service.eliminar(1L);

        verify(repository).findById(1L);
        verify(repository).delete(notificacion);
    }

    @Test
    @DisplayName("Debe lanzar excepcion al eliminar inexistente")
    void debeLanzarExcepcionEliminar() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.eliminar(1L));

        assertEquals(
                "Notificación no encontrada",
                ex.getMessage());

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Debe generar notificaciones de citas")
    void debeGenerarNotificacionesCitas() {

        CitaDTO cita = CitaDTO.builder()
                .idCita(1L)
                .idPaciente(10L)
                .idMedico(20L)
                .fechaIni(LocalDateTime.of(
                        2026, 1, 10, 10, 0))
                .estado("AGENDADA")
                .build();

        when(citasClient.listar())
                .thenReturn(List.of(cita));

        List<String> resultado =
                service.obtenerNotificacionesCitas();

        assertEquals(1, resultado.size());

        verify(citasClient).listar();
    }

    @Test
    @DisplayName("Debe generar notificaciones de recetas")
    void debeGenerarNotificacionesRecetas() {

        RecetaDTO receta = RecetaDTO.builder()
                .id(1L)
                .idPaciente(10L)
                .idMedico(20L)
                .fecha(LocalDate.now())
                .build();

        when(recetasClient.listar())
                .thenReturn(List.of(receta));

        List<String> resultado =
                service.obtenerNotificacionesRecetas();

        assertEquals(1, resultado.size());

        verify(recetasClient).listar();
    }

    @Test
    @DisplayName("Debe generar notificaciones de examenes")
    void debeGenerarNotificacionesExamenes() {

        ExamenDTO examen = ExamenDTO.builder()
                .idExamen(1L)
                .tipoExamen("Hemograma")
                .idPaciente(10L)
                .fechaSolicitud(LocalDateTime.now())
                .estado("PENDIENTE")
                .build();

        when(examenesClient.listar())
                .thenReturn(List.of(examen));

        List<String> resultado =
                service.obtenerNotificacionesExamenes();

        assertEquals(1, resultado.size());

        verify(examenesClient).listar();
    }
}