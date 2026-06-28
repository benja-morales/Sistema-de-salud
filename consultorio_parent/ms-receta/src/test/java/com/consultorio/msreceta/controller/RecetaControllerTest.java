package com.consultorio.msreceta.controller;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import com.consultorio.msreceta.model.MedicamentoReceta;
import com.consultorio.msreceta.model.Receta;
import com.consultorio.msreceta.service.RecetaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RecetaController.class)
class RecetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RecetaService recetaService;

    @Test
    @DisplayName("GET /api/recetas")
    void deberiaListarRecetas() throws Exception {

        Receta receta1 = new Receta();
        receta1.setId(1L);

        Receta receta2 = new Receta();
        receta2.setId(2L);

        when(recetaService.listar())
                .thenReturn(List.of(receta1, receta2));

        mockMvc.perform(get("/api/recetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(recetaService, times(1))
                .listar();
    }

    @Test
    @DisplayName("GET /api/recetas/{id}")
    void deberiaBuscarPorId() throws Exception {

        Receta receta = new Receta();
        receta.setId(1L);

        when(recetaService.buscarPorId(1L))
                .thenReturn(receta);

        mockMvc.perform(get("/api/recetas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(recetaService, times(1))
                .buscarPorId(1L);
    }

    @Test
    @DisplayName("GET /api/recetas/paciente/{idPaciente}")
    void deberiaBuscarPorPaciente() throws Exception {

        Receta receta = new Receta();
        receta.setId(1L);

        when(recetaService.buscarPorPaciente(10L))
                .thenReturn(List.of(receta));

        mockMvc.perform(get("/api/recetas/paciente/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(recetaService, times(1))
                .buscarPorPaciente(10L);
    }

    @Test
    @DisplayName("GET /api/recetas/medicos/{idMedico}")
    void deberiaBuscarPorMedico() throws Exception {

        Receta receta = new Receta();
        receta.setId(1L);

        when(recetaService.buscarPorMedico(5L))
                .thenReturn(List.of(receta));

        mockMvc.perform(get("/api/recetas/medicos/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(recetaService, times(1))
                .buscarPorMedico(5L);
    }

    @Test
    @DisplayName("POST /api/recetas")
    void deberiaGuardarReceta() throws Exception {

        MedicamentoReceta medicamento = new MedicamentoReceta();
        medicamento.setIdMedicamento(1L);
        medicamento.setNombreMedicamento("Paracetamol");
        medicamento.setCantidad(2);
        medicamento.setIndicaciones("Cada 8 horas");

        Receta receta = new Receta();
        receta.setIdPaciente(1L);
        receta.setIdMedico(2L);
        receta.setFecha(LocalDate.now());   // <-- FALTABA ESTO
        receta.setMedicamentos(List.of(medicamento));

        when(recetaService.guardar(any(Receta.class)))
                .thenReturn(receta);

        mockMvc.perform(post("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receta)))
                .andExpect(status().isOk());

        verify(recetaService, times(1))
                .guardar(any(Receta.class));
    }

    @Test
    @DisplayName("POST /api/recetas/{id}/medicamentos")
    void deberiaAgregarMedicamento() throws Exception {

        MedicamentoReceta medicamento = new MedicamentoReceta();
        medicamento.setIdMedicamento(1L);
        medicamento.setNombreMedicamento("Paracetamol");
        medicamento.setCantidad(2);
        medicamento.setIndicaciones("Cada 8 horas");

        Receta receta = new Receta();

        when(recetaService.agregarMedicamento(
                eq(1L),
                any(MedicamentoReceta.class)))
                .thenReturn(receta);

        mockMvc.perform(post("/api/recetas/1/medicamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicamento)))
                .andExpect(status().isOk());

        verify(recetaService, times(1))
                .agregarMedicamento(
                        eq(1L),
                        any(MedicamentoReceta.class));
    }

    @Test
    @DisplayName("DELETE /api/recetas/{id}/medicamentos/{medicamentoId}")
    void deberiaEliminarMedicamento() throws Exception {

        Receta receta = new Receta();

        when(recetaService.eliminarMedicamento(1L, 2L))
                .thenReturn(receta);

        mockMvc.perform(delete("/api/recetas/1/medicamentos/2"))
                .andExpect(status().isOk());

        verify(recetaService, times(1))
                .eliminarMedicamento(1L, 2L);
    }

    @Test
    @DisplayName("DELETE /api/recetas/{id}")
    void deberiaEliminarReceta() throws Exception {

        doNothing()
                .when(recetaService)
                .eliminar(1L);

        mockMvc.perform(delete("/api/recetas/1"))
                .andExpect(status().isOk());

        verify(recetaService, times(1))
                .eliminar(1L);
    }
}