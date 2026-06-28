package consultorio.ms_citas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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

import consultorio.mscitas.controller.CitasController;
import consultorio.mscitas.model.Citas;
import consultorio.mscitas.service.CitasService;

class CitasControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CitasService service;

    @InjectMocks
    private CitasController controller;

    private ObjectMapper objectMapper;

    private Citas cita;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        cita = new Citas();

        cita.setIdCita(1L);
        cita.setIdPaciente(10L);
        cita.setIdMedico(5L);
        cita.setFechaIni(LocalDateTime.now().plusDays(1));
        cita.setFechaTerm(LocalDateTime.now().plusDays(1).plusHours(1));
        cita.setEstado("PENDIENTE");
    }

    @Test
    void deberiaCrearCita() throws Exception {

        // ARRANGE
        when(service.crear(any(Citas.class)))
                .thenReturn(cita);

        // ACT + ASSERT
        mockMvc.perform(post("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cita)))
                .andExpect(status().isCreated());

        // Caso hipotético QA:
        // Se esperaba HTTP 201 y se obtuvo HTTP 400.
    }

    @Test
    void deberiaReagendarCita() throws Exception {

        // ARRANGE
        when(service.reagendar(eq(1L), any(Citas.class)))
                .thenReturn(cita);

        // ACT + ASSERT
        mockMvc.perform(put("/api/citas/1/reagendar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cita)))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaCancelarCita() throws Exception {

        // ARRANGE
        when(service.cancelar(1L))
                .thenReturn(cita);

        // ACT + ASSERT
        mockMvc.perform(put("/api/citas/1/cancelar"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaCambiarEstado() throws Exception {

        // ARRANGE
        cita.setEstado("CONFIRMADA");

        when(service.cambiarEstado(1L, "CONFIRMADA"))
                .thenReturn(cita);

        // ACT + ASSERT
        mockMvc.perform(put("/api/citas/1/estado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cita)))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaObtenerCitaPorId() throws Exception {

        // ARRANGE
        when(service.obtener(1L))
                .thenReturn(cita);

        // ACT + ASSERT
        mockMvc.perform(get("/api/citas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaListarTodasLasCitas() throws Exception {

        // ARRANGE
        when(service.listar())
                .thenReturn(List.of(cita));

        // ACT + ASSERT
        mockMvc.perform(get("/api/citas"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaBuscarPorPaciente() throws Exception {

        // ARRANGE
        when(service.porPaciente(10L))
                .thenReturn(List.of(cita));

        // ACT + ASSERT
        mockMvc.perform(get("/api/citas/paciente/10"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaBuscarPorMedico() throws Exception {

        // ARRANGE
        when(service.porMedico(5L))
                .thenReturn(List.of(cita));

        // ACT + ASSERT
        mockMvc.perform(get("/api/citas/medico/5"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaBuscarPorFecha() throws Exception {

        // ARRANGE
        LocalDate fecha = LocalDate.of(2026, 6, 21);

        when(service.porFecha(fecha))
                .thenReturn(List.of(cita));

        // ACT + ASSERT
        mockMvc.perform(get("/api/citas/fecha/21-06-2026"))
                .andExpect(status().isOk());
    }
}