package consultorio.msAtencionMedica.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import consultorio.msAtencionMedica.client.CitasClient;
import consultorio.msAtencionMedica.data.Cita;
import consultorio.msAtencionMedica.model.AtencionMedica;
import consultorio.msAtencionMedica.repository.AtencionMedicaRepository;

@ExtendWith(MockitoExtension.class)
class AtencionMedicaServiceTest {

    @Mock
    private AtencionMedicaRepository repo;

    @Mock
    private CitasClient citasClient;

    @InjectMocks
    private AtencionMedicaService service;

    private AtencionMedica atencion;
    private Cita cita;

    @BeforeEach
    void setUp() {

        atencion = new AtencionMedica();

        atencion.setIdAtencion(1L);
        atencion.setIdCita(8L);
        atencion.setDiagnostico("Dolor agudo");
        atencion.setObservaciones("Paciente estable");
        atencion.setFechaAtencion(LocalDateTime.now());

        cita = new Cita();
        cita.setEstado("PENDIENTE");
    }

    @Test
    void registrarDeberiaGuardarAtencion() {

        // ARRANGE
        when(citasClient.obtenerCita(8L))
                .thenReturn(cita);

        when(repo.findByIdCita(8L))
                .thenReturn(Optional.empty());

        when(repo.save(any(AtencionMedica.class)))
                .thenReturn(atencion);

        // ACT
        AtencionMedica resultado = service.registrar(atencion);

        // ASSERT
        assertNotNull(resultado);

        // VERIFY
        verify(repo).save(any(AtencionMedica.class));
    }

    @Test
    void registrarDeberiaLanzarErrorSiCitaNoExiste() {

        // ARRANGE
        when(citasClient.obtenerCita(8L))
                .thenThrow(new RuntimeException());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.registrar(atencion));

        assertEquals("La cita no existe",
                ex.getMessage());
    }

    @Test
    void registrarDeberiaLanzarErrorSiCitaNoEstaPendiente() {

        // ARRANGE
        cita.setEstado("CONFIRMADA");

        when(citasClient.obtenerCita(8L))
                .thenReturn(cita);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.registrar(atencion));

        assertEquals(
                "La cita no esta en estado PENDIENTE",
                ex.getMessage());
    }

    @Test
    void listarDeberiaRetornarLista() {

        when(repo.findAll())
                .thenReturn(List.of(atencion));

        List<AtencionMedica> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repo).findAll();
    }

    @Test
    void obtenerDeberiaRetornarAtencion() {

        when(repo.findById(1L))
                .thenReturn(Optional.of(atencion));

        AtencionMedica resultado = service.obtener(1L);

        assertNotNull(resultado);

        verify(repo).findById(1L);
    }

    @Test
    void asociarACitaDeberiaRegistrarAtencion() {

        // ARRANGE
        when(citasClient.obtenerCita(8L))
                .thenReturn(cita);

        when(repo.findByIdCita(8L))
                .thenReturn(Optional.empty());

        when(repo.save(any(AtencionMedica.class)))
                .thenReturn(atencion);

        // ACT
        AtencionMedica resultado = service.asociarACita(8L, atencion);

        // ASSERT
        assertNotNull(resultado);
    }

    @Test
    void agregarDiagnosticoDeberiaActualizarDiagnostico() {

        // ARRANGE
        when(repo.findById(1L))
                .thenReturn(Optional.of(atencion));

        when(repo.save(any(AtencionMedica.class)))
                .thenReturn(atencion);

        // ACT
        AtencionMedica resultado = service.agregarDiagnostico(1L, "Nuevo diagnóstico");

        // ASSERT
        assertNotNull(resultado);

        // VERIFY
        verify(repo).save(any(AtencionMedica.class));
    }

    @Test
    void agregarDiagnosticoDeberiaLanzarErrorSiEstaVacio() {

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.agregarDiagnostico(1L, ""));

        assertEquals(
                "El diagnóstico no puede estar vació",
                ex.getMessage());
    }

    @Test
    void agregarObservacionesDeberiaActualizarObservaciones() {

        // ARRANGE
        when(repo.findById(1L))
                .thenReturn(Optional.of(atencion));

        when(repo.save(any(AtencionMedica.class)))
                .thenReturn(atencion);

        // ACT
        AtencionMedica resultado = service.agregarobservaciones(1L, "Nueva observación");

        // ASSERT
        assertNotNull(resultado);

        // VERIFY
        verify(repo).save(any(AtencionMedica.class));
    }

    @Test
    void agregarObservacionesDeberiaLanzarErrorSiEstaVacia() {

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.agregarobservaciones(1L, ""));

        assertEquals(
                "Las observaciones no pueden estar vacías",
                ex.getMessage());
    }
}