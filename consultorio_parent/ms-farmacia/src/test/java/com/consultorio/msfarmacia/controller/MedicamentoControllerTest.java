package com.consultorio.msfarmacia.controller;

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

import com.consultorio.msfarmacia.model.Medicamento;
import com.consultorio.msfarmacia.service.MedicamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MedicamentoController.class)
class MedicamentoControllerTest {


@Autowired
private MockMvc mockMvc;

@Autowired
private ObjectMapper objectMapper;

@MockitoBean
private MedicamentoService service;

private Medicamento crearMedicamento() {
    return new Medicamento(
            1L,
            "Paracetamol",
            "Analgesico",
            100,
            1500.0,
            "Laboratorio Chile");
}

@Test
@DisplayName("GET /api/medicamentos - Debe listar medicamentos")
void debeListarMedicamentos() throws Exception {

    // ARRANGE
    Medicamento medicamento = crearMedicamento();

    when(service.listar())
            .thenReturn(List.of(medicamento));

    // ACT + ASSERT
    mockMvc.perform(get("/api/medicamentos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Paracetamol"))
            .andExpect(jsonPath("$[0].stock").value(100));

    // VERIFY
    verify(service).listar();
}

@Test
@DisplayName("GET /api/medicamentos/{id} - Debe buscar medicamento por id")
void debeBuscarMedicamentoPorId() throws Exception {

    // ARRANGE
    Medicamento medicamento = crearMedicamento();

    when(service.buscarId(1L))
            .thenReturn(medicamento);

    // ACT + ASSERT
    mockMvc.perform(get("/api/medicamentos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Paracetamol"));

    // VERIFY
    verify(service).buscarId(1L);
}

@Test
@DisplayName("POST /api/medicamentos - Debe guardar medicamento")
void debeGuardarMedicamento() throws Exception {

    // ARRANGE
    Medicamento medicamento = crearMedicamento();

    when(service.guardar(medicamento))
            .thenReturn(medicamento);

    // ACT + ASSERT
    mockMvc.perform(post("/api/medicamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(medicamento)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombre").value("Paracetamol"));

    // VERIFY
    verify(service).guardar(medicamento);
}

@Test
@DisplayName("PUT /api/medicamentos/{id} - Debe actualizar medicamento")
void debeActualizarMedicamento() throws Exception {

    // ARRANGE
    Medicamento medicamento = crearMedicamento();

    when(service.actualizar(1L, medicamento))
            .thenReturn(medicamento);

    // ACT + ASSERT
    mockMvc.perform(put("/api/medicamentos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(medicamento)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Paracetamol"));

    // VERIFY
    verify(service).actualizar(1L, medicamento);
}

@Test
@DisplayName("DELETE /api/medicamentos/{id} - Debe eliminar medicamento")
void debeEliminarMedicamento() throws Exception {

    // ARRANGE
    doNothing().when(service).eliminar(1L);

    // ACT + ASSERT
    mockMvc.perform(delete("/api/medicamentos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value("Medicamento eliminado"));

    // VERIFY
    verify(service).eliminar(1L);
}

/*
 * Casos hipotéticos de falla QA:
 *
 * 1. GET devuelve 500 en vez de 200.
 * 2. POST devuelve 200 en vez de 201.
 * 3. PUT no actualiza los datos enviados.
 * 4. DELETE devuelve mensaje incorrecto.
 * 5. El JSON no contiene los campos esperados.
 */


}
