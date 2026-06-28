package consultorio.ms_citas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import consultorio.mscitas.client.MedicoClient;
import consultorio.mscitas.client.PacienteClient;
import consultorio.mscitas.model.Citas;
import consultorio.mscitas.repository.CitasRepository;
import consultorio.mscitas.service.CitasService;

@ExtendWith(MockitoExtension.class)
class CitasServiceTest {

    @Mock
    private CitasRepository repoCi;

    @Mock
    private MedicoClient medicoClient;

    @Mock
    private PacienteClient pacienteClient;

    @InjectMocks
    private CitasService service;

    private Citas cita;

    @BeforeEach
    void setUp() {

        cita = new Citas();

        cita.setIdCita(1L);
        cita.setIdPaciente(10L);
        cita.setIdMedico(5L);
        cita.setFechaIni(LocalDateTime.now().plusDays(1));
        cita.setFechaTerm(LocalDateTime.now().plusDays(1).plusHours(1));
        cita.setEstado("PENDIENTE");
    }

    @Test
    void crearDeberiaGuardarCita() {

        // ARRANGE
        when(repoCi.existsByIdMedicoAndFechaIniLessThanAndFechaTermGreaterThan(
                any(), any(), any()))
                .thenReturn(false);

        when(repoCi.save(any(Citas.class)))
                .thenReturn(cita);

        // ACT
        Citas resultado = service.crear(cita);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());

        // VERIFY
        verify(repoCi).save(any(Citas.class));
    }

    @Test
    void crearDeberiaLanzarErrorSiHorarioOcupado() {

        // ARRANGE
        when(repoCi.existsByIdMedicoAndFechaIniLessThanAndFechaTermGreaterThan(
                any(), any(), any()))
                .thenReturn(true);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.crear(cita));

        assertEquals(
                "El medico ya tiene una cita en ese horario",
                ex.getMessage());
    }

    @Test
    void reagendarDeberiaModificarCita() {

        // ARRANGE
        when(repoCi.findById(1L))
                .thenReturn(Optional.of(cita));

        when(repoCi.existsByIdMedicoAndFechaIniLessThanAndFechaTermGreaterThan(
                any(), any(), any()))
                .thenReturn(false);

        when(repoCi.save(any(Citas.class)))
                .thenReturn(cita);

        // ACT
        Citas resultado = service.reagendar(1L, cita);

        // ASSERT
        assertNotNull(resultado);

        // VERIFY
        verify(repoCi).save(any(Citas.class));
    }

    @Test
    void cancelarDeberiaCambiarEstadoACancelada() {

        // ARRANGE
        when(repoCi.findById(1L))
                .thenReturn(Optional.of(cita));

        when(repoCi.save(any(Citas.class)))
                .thenReturn(cita);

        // ACT
        Citas resultado = service.cancelar(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("CANCELADA", resultado.getEstado());

        // VERIFY
        verify(repoCi).save(any(Citas.class));
    }

    @Test
    void cambiarEstadoDeberiaActualizarEstado() {

        // ARRANGE
        when(repoCi.findById(1L))
                .thenReturn(Optional.of(cita));

        when(repoCi.save(any(Citas.class)))
                .thenReturn(cita);

        // ACT
        Citas resultado = service.cambiarEstado(1L, "CONFIRMADA");

        // ASSERT
        assertNotNull(resultado);
        assertEquals("CONFIRMADA", resultado.getEstado());

        // VERIFY
        verify(repoCi).save(any(Citas.class));
    }

    @Test
    void cambiarEstadoDeberiaLanzarErrorSiEstadoInvalido() {

        // ARRANGE
        when(repoCi.findById(1L))
                .thenReturn(Optional.of(cita));

        // ACT + ASSERT
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.cambiarEstado(1L, "FINALIZADA"));

        assertEquals("Estado inválido", ex.getMessage());
    }

    @Test
    void obtenerDeberiaRetornarCita() {

        // ARRANGE
        when(repoCi.findById(1L))
                .thenReturn(Optional.of(cita));

        // ACT
        Citas resultado = service.obtener(1L);

        // ASSERT
        assertNotNull(resultado);

        // VERIFY
        verify(repoCi).findById(1L);
    }

    @Test
    void listarDeberiaRetornarLista() {

        when(repoCi.findAll())
                .thenReturn(List.of(cita));

        List<Citas> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repoCi).findAll();
    }

    @Test
    void porPacienteDeberiaRetornarLista() {

        when(repoCi.findByIdPaciente(10L))
                .thenReturn(List.of(cita));

        List<Citas> resultado = service.porPaciente(10L);

        assertEquals(1, resultado.size());

        verify(repoCi).findByIdPaciente(10L);
    }

    @Test
    void porMedicoDeberiaRetornarLista() {

        when(repoCi.findByIdMedico(5L))
                .thenReturn(List.of(cita));

        List<Citas> resultado = service.porMedico(5L);

        assertEquals(1, resultado.size());

        verify(repoCi).findByIdMedico(5L);
    }

    @Test
    void porFechaDeberiaRetornarLista() {

        LocalDate fecha = LocalDate.now();

        when(repoCi.findByFechaIniBetween(any(), any()))
                .thenReturn(List.of(cita));

        List<Citas> resultado = service.porFecha(fecha);

        assertEquals(1, resultado.size());

        verify(repoCi).findByFechaIniBetween(any(), any());
    }

    @Test
    void totalPorMedicoDeberiaRetornarCantidad() {

        when(repoCi.countByIdMedico(5L))
                .thenReturn(3L);

        Long total = service.totalPorMedico(5L);

        assertEquals(3L, total);

        verify(repoCi).countByIdMedico(5L);
    }
}