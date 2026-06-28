package com.consultorio.msfacturacion.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.consultorio.msfacturacion.modelo.FacturaDetalle;
import com.consultorio.msfacturacion.modelo.Facturacion;
import com.consultorio.msfacturacion.service.FacturacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class FacturacionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FacturacionService facturaService;

    @InjectMocks
    private FacturacionController controller;

    private ObjectMapper objectMapper;

    private Facturacion factura;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setIdDetalle(1L);
        detalle.setConcepto("Consulta General");
        detalle.setPrecioUnitario(20000.0);
        detalle.setCantidad(2);

        factura = new Facturacion();
        factura.setIdFactura(1L);
        factura.setIdPaciente(10L);
        factura.setFechaEmision(LocalDateTime.now());
        factura.setEstado("PENDIENTE");
        factura.setDetalles(List.of(detalle));
        factura.setTotal(40000.0);
    }

    @Test
    void deberiaCrearFactura() throws Exception {

        // ARRANGE
        when(facturaService.crearFactura(any(Facturacion.class)))
                .thenReturn(factura);

        // ACT + ASSERT
        mockMvc.perform(post("/api/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isCreated());

        // Caso hipotético QA:
        // Se esperaba HTTP 201 Created y se obtuvo HTTP 400 Bad Request.
    }

    @Test
    void deberiaObtenerFacturaPorId() throws Exception {

        // ARRANGE
        when(facturaService.obtenerPorId(1L))
                .thenReturn(Optional.of(factura));

        // ACT + ASSERT
        mockMvc.perform(get("/api/facturas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaRetornar404SiFacturaNoExiste() throws Exception {

        // ARRANGE
        when(facturaService.obtenerPorId(1L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        mockMvc.perform(get("/api/facturas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deberiaListarFacturasPorPaciente() throws Exception {

        // ARRANGE
        when(facturaService.listarPorPaciente(10L))
                .thenReturn(List.of(factura));

        // ACT + ASSERT
        mockMvc.perform(get("/api/facturas/paciente/10"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaActualizarFactura() throws Exception {

        // ARRANGE
        when(facturaService.obtenerPorId(1L))
                .thenReturn(Optional.of(factura));

        when(facturaService.crearFactura(any(Facturacion.class)))
                .thenReturn(factura);

        // ACT + ASSERT
        mockMvc.perform(put("/api/facturas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaListarFacturasPorFecha() throws Exception {

        // ARRANGE
        when(facturaService.listarPorFecha(LocalDate.of(2026, 6, 10)))
                .thenReturn(List.of(factura));

        // ACT + ASSERT
        mockMvc.perform(get("/api/facturas/fecha/2026-06-10"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaEliminarFactura() throws Exception {

        // ACT + ASSERT
        mockMvc.perform(delete("/api/facturas/1"))
                .andExpect(status().isNoContent());
    }
}