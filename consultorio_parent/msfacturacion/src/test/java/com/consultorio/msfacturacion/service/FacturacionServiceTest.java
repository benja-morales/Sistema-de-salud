package com.consultorio.msfacturacion.service;

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

import com.consultorio.msfacturacion.modelo.FacturaDetalle;
import com.consultorio.msfacturacion.modelo.Facturacion;
import com.consultorio.msfacturacion.repository.FacturacionRepository;

@ExtendWith(MockitoExtension.class)
class FacturacionServiceTest {

    @Mock
    private FacturacionRepository facturaRepository;

    @InjectMocks
    private FacturacionService service;

    private Facturacion factura;

    @BeforeEach
    void setUp() {

        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setIdDetalle(1L);
        detalle.setConcepto("Consulta General");
        detalle.setPrecioUnitario(15000.0);
        detalle.setCantidad(2);

        factura = new Facturacion();
        factura.setIdFactura(1L);
        factura.setIdPaciente(20L);
        factura.setFechaEmision(LocalDateTime.now());
        factura.setEstado("PENDIENTE");
        factura.setDetalles(List.of(detalle));
    }

    @Test
    void crearFacturaDeberiaGuardarFactura() {

        // ARRANGE
        when(facturaRepository.save(any(Facturacion.class)))
                .thenReturn(factura);

        // ACT
        Facturacion resultado = service.crearFactura(factura);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(30000.0, factura.getTotal());

        // VERIFY
        verify(facturaRepository).save(any(Facturacion.class));
    }

    @Test
    void listarPorPacienteDeberiaRetornarLista() {

        // ARRANGE
        when(facturaRepository.findByIdPaciente(20L))
                .thenReturn(List.of(factura));

        // ACT
        List<Facturacion> resultado = service.listarPorPaciente(20L);

        // ASSERT
        assertEquals(1, resultado.size());

        // VERIFY
        verify(facturaRepository).findByIdPaciente(20L);
    }

    @Test
    void actualizarFacturaDeberiaActualizarDatos() {

        // ARRANGE
        when(facturaRepository.findById(1L))
                .thenReturn(Optional.of(factura));

        when(facturaRepository.save(any(Facturacion.class)))
                .thenReturn(factura);

        // ACT
        Facturacion resultado = service.actualizarFactura(1L, factura);

        // ASSERT
        assertNotNull(resultado);

        // VERIFY
        verify(facturaRepository).save(any(Facturacion.class));
    }

    @Test
    void actualizarFacturaDeberiaLanzarExcepcionSiNoExiste() {

        // ARRANGE
        when(facturaRepository.findById(1L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.actualizarFactura(1L, factura));

        assertEquals(
                "Factura no encontrada",
                ex.getMessage());
    }

    @Test
    void listarPorFechaDeberiaRetornarLista() {

        // ARRANGE
        LocalDate fecha = LocalDate.now();

        when(facturaRepository.findByFechaEmisionBetween(any(), any()))
                .thenReturn(List.of(factura));

        // ACT
        List<Facturacion> resultado = service.listarPorFecha(fecha);

        // ASSERT
        assertEquals(1, resultado.size());

        // VERIFY
        verify(facturaRepository)
                .findByFechaEmisionBetween(any(), any());
    }

    @Test
    void obtenerPorIdDeberiaRetornarFactura() {

        // ARRANGE
        when(facturaRepository.findById(1L))
                .thenReturn(Optional.of(factura));

        // ACT
        Optional<Facturacion> resultado = service.obtenerPorId(1L);

        // ASSERT
        assertTrue(resultado.isPresent());

        // VERIFY
        verify(facturaRepository).findById(1L);
    }

    @Test
    void eliminarFacturaDeberiaEliminarRegistro() {

        // ACT
        service.eliminarFactura(1L);

        // VERIFY
        verify(facturaRepository).deleteById(1L);
    }
}