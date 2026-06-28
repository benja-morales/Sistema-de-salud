package consultorio.ms_PersonalDeSalud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import consultorio.msmedico.model.Medico;
import consultorio.msmedico.repository.MedicoRepository;
import consultorio.msmedico.service.MedicoService;

@ExtendWith(MockitoExtension.class)
class MedicoServiceTest {

    @Mock
    private MedicoRepository repoMed;

    @InjectMocks
    private MedicoService service;

    private Medico medico;

    @BeforeEach
    void setUp() {

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
    void registrarDeberiaGuardarMedico() {

        // ARRANGE
        when(repoMed.existsByRutMed(medico.getRutMed()))
                .thenReturn(false);

        when(repoMed.existsByEmail(medico.getEmail()))
                .thenReturn(false);

        when(repoMed.save(any(Medico.class)))
                .thenReturn(medico);

        // ACT
        Medico resultado = service.registrar(medico);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getPnomMed());

        // VERIFY
        verify(repoMed).save(medico);
    }

    @Test
    void registrarDeberiaLanzarErrorSiRutExiste() {

        // ARRANGE
        when(repoMed.existsByRutMed(medico.getRutMed()))
                .thenReturn(true);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.registrar(medico));

        assertEquals("Error: EL rut ya existe",
                ex.getMessage());

        verify(repoMed, never()).save(any());
    }

    @Test
    void registrarDeberiaLanzarErrorSiEmailExiste() {

        // ARRANGE
        when(repoMed.existsByRutMed(medico.getRutMed()))
                .thenReturn(false);

        when(repoMed.existsByEmail(medico.getEmail()))
                .thenReturn(true);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.registrar(medico));

        assertEquals("Error: El email ya estaba registrado",
                ex.getMessage());

        verify(repoMed, never()).save(any());
    }

    @Test
    void registrarDeberiaLanzarErrorSiEspecialidadEstaVacia() {

        // ARRANGE
        medico.setEspecialidad("");

        when(repoMed.existsByRutMed(anyInt()))
                .thenReturn(false);

        when(repoMed.existsByEmail(anyString()))
                .thenReturn(false);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.registrar(medico));

        assertEquals(
                "Error: La especialidad es obligatoria",
                ex.getMessage());

        verify(repoMed, never()).save(any());
    }

    @Test
    void actualizarDeberiaModificarMedico() {

        // ARRANGE
        Medico actualizado = new Medico();
        actualizado.setPnomMed("Pedro");
        actualizado.setSnomMed("Luis");
        actualizado.setApePatMed("Ramirez");
        actualizado.setApeMatMed("Diaz");
        actualizado.setRutMed(11111111);
        actualizado.setDvMed("1");
        actualizado.setEspecialidad("Neurologia");
        actualizado.setEmail("pedro@correo.cl");
        actualizado.setTelefono("999999999");
        actualizado.setFechaContratacion(LocalDate.now());
        actualizado.setEstado(true);

        when(repoMed.findById(1L))
                .thenReturn(Optional.of(medico));

        when(repoMed.save(any(Medico.class)))
                .thenReturn(medico);

        // ACT
        Medico resultado = service.actualizar(1L, actualizado);

        // ASSERT
        assertNotNull(resultado);

        // VERIFY
        verify(repoMed).findById(1L);
        verify(repoMed).save(any(Medico.class));
    }

    @Test
    void listarTodoDeberiaRetornarLista() {

        when(repoMed.findAll())
                .thenReturn(List.of(medico));

        List<Medico> resultado = service.listarTodo();

        assertEquals(1, resultado.size());

        verify(repoMed).findAll();
    }

    @Test
    void obtenerPorIdDeberiaRetornarMedico() {

        when(repoMed.findById(1L))
                .thenReturn(Optional.of(medico));

        Optional<Medico> resultado = service.obtenerPorId(1L);

        assertTrue(resultado.isPresent());

        verify(repoMed).findById(1L);
    }

    @Test
    void buscarPorRutDeberiaRetornarMedico() {

        when(repoMed.findByRutMedAndDvMed(12345678, "K"))
                .thenReturn(Optional.of(medico));

        Optional<Medico> resultado = service.buscarPorRut(12345678, "K");

        assertTrue(resultado.isPresent());

        verify(repoMed)
                .findByRutMedAndDvMed(12345678, "K");
    }

    @Test
    void buscarPorNombreDeberiaRetornarLista() {

        when(repoMed
                .findByPnomMedContainingIgnoreCaseOrSnomMedContainingIgnoreCase(
                        "Juan", "Juan"))
                .thenReturn(List.of(medico));

        List<Medico> resultado = service.buscarPorNombre("Juan");

        assertEquals(1, resultado.size());

        verify(repoMed)
                .findByPnomMedContainingIgnoreCaseOrSnomMedContainingIgnoreCase(
                        "Juan", "Juan");
    }

    @Test
    void buscarPorEspecialidadDeberiaRetornarLista() {

        when(repoMed.findByEspecialidadContainingIgnoreCase("Cardiologia"))
                .thenReturn(List.of(medico));

        List<Medico> resultado = service.buscarPorEspecialidad("Cardiologia");

        assertEquals(1, resultado.size());

        verify(repoMed)
                .findByEspecialidadContainingIgnoreCase("Cardiologia");
    }

    @Test
    void eliminarLogicDeberiaRetornarTrue() {

        when(repoMed.findById(1L))
                .thenReturn(Optional.of(medico));

        boolean resultado = service.eliminarLogic(1L);

        assertTrue(resultado);

        verify(repoMed).delete(medico);
    }

    @Test
    void eliminarLogicDeberiaRetornarFalseCuandoNoExiste() {

        when(repoMed.findById(1L))
                .thenReturn(Optional.empty());

        boolean resultado = service.eliminarLogic(1L);

        assertFalse(resultado);

        verify(repoMed, never()).delete(any());
    }
}