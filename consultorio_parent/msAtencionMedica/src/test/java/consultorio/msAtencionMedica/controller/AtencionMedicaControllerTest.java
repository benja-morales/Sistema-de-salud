package consultorio.msAtencionMedica.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import consultorio.msAtencionMedica.model.AtencionMedica;
import consultorio.msAtencionMedica.service.AtencionMedicaService;

class AtencionMedicaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AtencionMedicaService service;

    @InjectMocks
    private AtencionMedicaController controller;

    private ObjectMapper objectMapper;

    private AtencionMedica atencion;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        atencion = new AtencionMedica();

        atencion.setIdAtencion(1L);
        atencion.setIdCita(8L);
        atencion.setDiagnostico("Dolor agudo");
        atencion.setObservaciones("Paciente estable");
        atencion.setFechaAtencion(LocalDateTime.now());
    }

    @Test
    void deberiaListarAtenciones() throws Exception {

        // ARRANGE
        when(service.listar())
                .thenReturn(List.of(atencion));

        // ACT + ASSERT
        mockMvc.perform(get("/api/atenciones"))
                .andExpect(status().isOk());

        // Caso hipotético QA:
        // Se esperaba HTTP 200 y se obtuvo HTTP 204.
    }

    @Test
    void deberiaRegistrarAtencion() throws Exception {

        // ARRANGE
        when(service.registrar(any(AtencionMedica.class)))
                .thenReturn(atencion);

        // ACT + ASSERT
        mockMvc.perform(post("/api/atenciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atencion)))
                .andExpect(status().isCreated());
    }

    @Test
    void deberiaAsociarAtencionACita() throws Exception {

        // ARRANGE
        when(service.asociarACita(eq(8L), any(AtencionMedica.class)))
                .thenReturn(atencion);

        // ACT + ASSERT
        mockMvc.perform(post("/api/atenciones/cita/8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atencion)))
                .andExpect(status().isCreated());
    }

    @Test
    void deberiaObtenerAtencionPorId() throws Exception {

        // ARRANGE
        when(service.obtener(1L))
                .thenReturn(atencion);

        // ACT + ASSERT
        mockMvc.perform(get("/api/atenciones/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaAgregarDiagnostico() throws Exception {

        // ARRANGE
        when(service.agregarDiagnostico(1L, "Dolor agudo"))
                .thenReturn(atencion);

        // ACT + ASSERT
        mockMvc.perform(put("/api/atenciones/1/diagnostico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atencion)))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaAgregarObservaciones() throws Exception {

        // ARRANGE
        when(service.agregarobservaciones(1L, "Paciente estable"))
                .thenReturn(atencion);

        // ACT + ASSERT
        mockMvc.perform(put("/api/atenciones/1/observaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atencion)))
                .andExpect(status().isOk());
    }
}
