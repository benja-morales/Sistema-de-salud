package com.consultorio.ms_examen.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.consultorio.ms_examen.modelo.Examen;
import com.consultorio.ms_examen.service.ExamenService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ExamenController.class)
class ExamenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamenService examenService;

    @Test
    void debeRetornarExamenPorId() throws Exception {

        Examen examen = new Examen();
        examen.setIdExamen(1L);
        examen.setTipoExamen("Radiografía");
        examen.setIdPaciente(1L);

        when(examenService.buscar(1L))
                .thenReturn(examen);

        mockMvc.perform(get("/api/examenes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoExamen")
                        .value("Radiografía"));
    }
}