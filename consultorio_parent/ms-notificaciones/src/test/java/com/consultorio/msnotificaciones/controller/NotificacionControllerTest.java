package com.consultorio.msnotificaciones.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.consultorio.msnotificaciones.model.Notificacion;
import com.consultorio.msnotificaciones.service.NotificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(NotificacionController.class)
class NotificacionControllerTest {


@Autowired
private MockMvc mockMvc;

@Autowired
private ObjectMapper objectMapper;

@MockitoBean
private NotificacionService service;

private Notificacion crearNotificacion() {

    return Notificacion.builder()
            .id(1L)
            .modulo("CITAS")
            .mensaje("Nueva cita registrada")
            .fecha(LocalDateTime.now())
            .leida(false)
            .build();
}

@Test
@DisplayName("GET /api/notificaciones")
void debeListarNotificaciones() throws Exception {

    // ARRANGE
    Notificacion notificacion = crearNotificacion();

    when(service.listar())
            .thenReturn(List.of(notificacion));

    // ACT + ASSERT
    mockMvc.perform(get("/api/notificaciones"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].modulo").value("CITAS"));

    // VERIFY
    verify(service).listar();
}

@Test
@DisplayName("GET /api/notificaciones/{id}")
void debeBuscarPorId() throws Exception {

    // ARRANGE
    Notificacion notificacion = crearNotificacion();

    when(service.buscarPorId(1L))
            .thenReturn(notificacion);

    // ACT + ASSERT
    mockMvc.perform(get("/api/notificaciones/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.mensaje")
                    .value("Nueva cita registrada"));

    // VERIFY
    verify(service).buscarPorId(1L);
}

@Test
@DisplayName("POST /api/notificaciones")
void debeGuardarNotificacion() throws Exception {

    // ARRANGE
    Notificacion notificacion = crearNotificacion();

    when(service.guardar(notificacion))
            .thenReturn(notificacion);

    // ACT + ASSERT
    mockMvc.perform(post("/api/notificaciones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(notificacion)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.modulo")
                    .value("CITAS"));

    // VERIFY
    verify(service).guardar(notificacion);
}

@Test
@DisplayName("PUT /api/notificaciones/{id}/leer")
void debeMarcarComoLeida() throws Exception {

    // ARRANGE
    Notificacion notificacion = crearNotificacion();
    notificacion.setLeida(true);

    when(service.marcarComoLeida(1L))
            .thenReturn(notificacion);

    // ACT + ASSERT
    mockMvc.perform(put("/api/notificaciones/1/leer"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.leida").value(true));

    // VERIFY
    verify(service).marcarComoLeida(1L);
}

@Test
@DisplayName("GET /api/notificaciones/modulo/{modulo}")
void debeBuscarPorModulo() throws Exception {

    // ARRANGE
    Notificacion notificacion = crearNotificacion();

    when(service.buscarPorModulo("CITAS"))
            .thenReturn(List.of(notificacion));

    // ACT + ASSERT
    mockMvc.perform(get("/api/notificaciones/modulo/CITAS"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].modulo")
                    .value("CITAS"));

    // VERIFY
    verify(service).buscarPorModulo("CITAS");
}

@Test
@DisplayName("GET /api/notificaciones/noleidas")
void debeBuscarNoLeidas() throws Exception {

    // ARRANGE
    Notificacion notificacion = crearNotificacion();

    when(service.buscarNoLeidas())
            .thenReturn(List.of(notificacion));

    // ACT + ASSERT
    mockMvc.perform(get("/api/notificaciones/noleidas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].leida")
                    .value(false));

    // VERIFY
    verify(service).buscarNoLeidas();
}

@Test
@DisplayName("DELETE /api/notificaciones/{id}")
void debeEliminarNotificacion() throws Exception {

    // ARRANGE
    doNothing().when(service)
            .eliminar(1L);

    // ACT + ASSERT
    mockMvc.perform(delete("/api/notificaciones/1"))
            .andExpect(status().isNoContent());

    // VERIFY
    verify(service).eliminar(1L);
}

@Test
@DisplayName("GET /api/notificaciones/citas")
void debeObtenerNotificacionesCitas() throws Exception {

    // ARRANGE
    when(service.obtenerNotificacionesCitas())
            .thenReturn(List.of(
                    "Cita del paciente 1"));

    // ACT + ASSERT
    mockMvc.perform(get("/api/notificaciones/citas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]")
                    .value("Cita del paciente 1"));

    // VERIFY
    verify(service).obtenerNotificacionesCitas();
}

@Test
@DisplayName("GET /api/notificaciones/recetas")
void debeObtenerNotificacionesRecetas() throws Exception {

    // ARRANGE
    when(service.obtenerNotificacionesRecetas())
            .thenReturn(List.of(
                    "Receta creada para paciente 1"));

    // ACT + ASSERT
    mockMvc.perform(get("/api/notificaciones/recetas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]")
                    .value("Receta creada para paciente 1"));

    // VERIFY
    verify(service).obtenerNotificacionesRecetas();
}

@Test
@DisplayName("GET /api/notificaciones/examenes")
void debeObtenerNotificacionesExamenes() throws Exception {

    // ARRANGE
    when(service.obtenerNotificacionesExamenes())
            .thenReturn(List.of(
                    "Examen Hemograma"));

    // ACT + ASSERT
    mockMvc.perform(get("/api/notificaciones/examenes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]")
                    .value("Examen Hemograma"));

    // VERIFY
    verify(service).obtenerNotificacionesExamenes();
}

/*
 * Casos hipotéticos QA:
 *
 * - GET devuelve 500.
 * - POST no guarda la notificación.
 * - PUT no cambia leida a true.
 * - DELETE devuelve 200 en vez de 204.
 * - Endpoints de citas, recetas o exámenes
 *   retornan listas vacías incorrectamente.
 */


}
