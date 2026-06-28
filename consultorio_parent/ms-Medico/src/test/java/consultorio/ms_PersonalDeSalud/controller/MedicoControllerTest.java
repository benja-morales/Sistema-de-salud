package consultorio.ms_PersonalDeSalud.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import consultorio.msmedico.controller.MedicoController;
import consultorio.msmedico.model.Medico;
import consultorio.msmedico.service.MedicoService;

@ExtendWith(MockitoExtension.class)
class MedicoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MedicoService service;

    @InjectMocks
    private MedicoController controller;

    private ObjectMapper objectMapper;

    private Medico medico;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper()
                .findAndRegisterModules();

        medico = new Medico();

        medico.setIdMed(1L);
        medico.setPnomMed("Juan");
        medico.setSnomMed("Carlos");
        medico.setApePatMed("Perez");
        medico.setApeMatMed("Gonzalez");
        medico.setRutMed(12345678);
        medico.setDvMed("K");
        medico.setEspecialidad("Cardiologia");
        medico.setEmail("juan@consultorio.cl");
        medico.setTelefono("+56912345678");
        medico.setFechaContratacion(LocalDate.of(2026, 1, 15));
        medico.setEstado(true);
    }

    @Test
    void deberiaListarTodosLosMedicos() throws Exception {

        when(service.listarTodo())
                .thenReturn(List.of(medico));

        mockMvc.perform(get("/api/medicos"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaObtenerMedicoPorId() throws Exception {

        when(service.obtenerPorId(1L))
                .thenReturn(Optional.of(medico));

        mockMvc.perform(get("/api/medicos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaRegistrarMedico() throws Exception {

        when(service.registrar(any(Medico.class)))
                .thenReturn(medico);

        mockMvc.perform(post("/api/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isCreated());
    }

    @Test
    void deberiaActualizarMedico() throws Exception {

        when(service.actualizar(eq(1L), any(Medico.class)))
                .thenReturn(medico);

        mockMvc.perform(put("/api/medicos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaEliminarMedico() throws Exception {

        when(service.eliminarLogic(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/api/medicos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaBuscarPorRut() throws Exception {

        when(service.buscarPorRut(12345678, "K"))
                .thenReturn(Optional.of(medico));

        mockMvc.perform(get("/api/medicos/rut/12345678/K"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaBuscarPorNombre() throws Exception {

        when(service.buscarPorNombre("Juan"))
                .thenReturn(List.of(medico));

        mockMvc.perform(get("/api/medicos/buscar/nombre/Juan"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaBuscarPorEspecialidad() throws Exception {

        when(service.buscarPorEspecialidad("Cardiologia"))
                .thenReturn(List.of(medico));

        mockMvc.perform(get("/api/medicos/buscar/esp/Cardiologia"))
                .andExpect(status().isOk());
    }
}