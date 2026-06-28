package com.consultorio.msfarmacia.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.consultorio.msfarmacia.model.Medicamento;
import com.consultorio.msfarmacia.repository.MedicamentoRepository;

@ExtendWith(MockitoExtension.class)
class MedicamentoServiceTest {

    @Mock
    private MedicamentoRepository repository;

    @InjectMocks
    private MedicamentoService service;

    private Medicamento medicamento;

    @BeforeEach
    void setUp() {

        medicamento = new Medicamento(
                1L,
                "Paracetamol",
                "Analgesico",
                100,
                1500.0,
                "Laboratorio Chile");
    }

    @Test
    @DisplayName("Debe listar medicamentos")
    void debeListarMedicamentos() {

        // ARRANGE
        when(repository.findAll())
                .thenReturn(List.of(medicamento));

        // ACT
        List<Medicamento> resultado = service.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Paracetamol", resultado.get(0).getNombre());

        // VERIFY
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Debe buscar medicamento por id")
    void debeBuscarMedicamentoPorId() {

        // ARRANGE
        when(repository.findById(1L))
                .thenReturn(Optional.of(medicamento));

        // ACT
        Medicamento resultado = service.buscarId(1L);

        // ASSERT
        assertEquals(1L, resultado.getId());

        // VERIFY
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando no existe el medicamento")
    void debeLanzarExcepcionAlBuscarIdInexistente() {

        // ARRANGE
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.buscarId(1L));

        assertEquals(
                "Medicamento no encontrado",
                exception.getMessage());

        // VERIFY
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Debe guardar medicamento correctamente")
    void debeGuardarMedicamento() {

        // ARRANGE
        when(repository.existsByNombre(medicamento.getNombre()))
                .thenReturn(false);

        when(repository.save(medicamento))
                .thenReturn(medicamento);

        // ACT
        Medicamento resultado = service.guardar(medicamento);

        // ASSERT
        assertEquals("Paracetamol", resultado.getNombre());

        // VERIFY
        verify(repository).existsByNombre(medicamento.getNombre());
        verify(repository).save(medicamento);
    }

    @Test
    @DisplayName("Debe impedir guardar medicamento duplicado")
    void debeImpedirGuardarMedicamentoDuplicado() {

        // ARRANGE
        when(repository.existsByNombre(medicamento.getNombre()))
                .thenReturn(true);

        // ACT + ASSERT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.guardar(medicamento));

        assertEquals(
                "El medicamento ya existe",
                exception.getMessage());

        // VERIFY
        verify(repository).existsByNombre(medicamento.getNombre());
    }

    @Test
    @DisplayName("Debe actualizar medicamento")
    void debeActualizarMedicamento() {

        // ARRANGE
        Medicamento actualizado = new Medicamento(
                1L,
                "Paracetamol",
                "Analgesico actualizado",
                200,
                2000.0,
                "Laboratorio Nuevo");

        when(repository.findById(1L))
                .thenReturn(Optional.of(medicamento));

        when(repository.save(org.mockito.ArgumentMatchers.any(Medicamento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        Medicamento resultado = service.actualizar(1L, actualizado);

        // ASSERT
        assertEquals("Analgesico actualizado", resultado.getDescripcion());
        assertEquals(200, resultado.getStock());
        assertEquals(2000.0, resultado.getPrecio());

        // VERIFY
        verify(repository).findById(1L);
        verify(repository)
                .save(org.mockito.ArgumentMatchers.any(Medicamento.class));
    }

    @Test
    @DisplayName("Debe eliminar medicamento")
    void debeEliminarMedicamento() {

        // ARRANGE
        when(repository.existsById(1L))
                .thenReturn(true);

        doNothing().when(repository)
                .deleteById(1L);

        // ACT
        service.eliminar(1L);

        // VERIFY
        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe impedir eliminar medicamento inexistente")
    void debeImpedirEliminarMedicamentoInexistente() {

        // ARRANGE
        when(repository.existsById(1L))
                .thenReturn(false);

        // ACT + ASSERT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.eliminar(1L));

        assertEquals(
                "No se puede eliminar. Medicamento no encontrado",
                exception.getMessage());

        // VERIFY
        verify(repository).existsById(1L);
    }

    @Test
    @DisplayName("Debe impedir guardar medicamento con precio negativo")
    void debeImpedirGuardarPrecioInvalido() {

        // ARRANGE
        medicamento.setPrecio(-100.0);

        // ACT + ASSERT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.guardar(medicamento));

        assertEquals(
                "El precio debe ser mayor a 0",
                exception.getMessage());
    }

    @Test
    @DisplayName("Debe impedir guardar medicamento con stock negativo")
    void debeImpedirGuardarStockNegativo() {

        // ARRANGE
        medicamento.setStock(-1);

        // ACT + ASSERT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.guardar(medicamento));

        assertEquals(
                "El stock no puede ser negativo",
                exception.getMessage());
    }

    @Test
    @DisplayName("Debe impedir guardar medicamento con nombre muy corto")
    void debeImpedirGuardarNombreCorto() {

        // ARRANGE
        medicamento.setNombre("AB");

        // ACT + ASSERT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.guardar(medicamento));

        assertEquals(
                "El nombre debe tener al menos 3 caracteres",
                exception.getMessage());
    }

    @Test
    @DisplayName("Debe impedir guardar medicamento con descripcion corta")
    void debeImpedirGuardarDescripcionCorta() {

        // ARRANGE
        medicamento.setDescripcion("ABC");

        // ACT + ASSERT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.guardar(medicamento));

        assertEquals(
                "La descripcion es demasiado corta",
                exception.getMessage());
    }

    /*
     * Casos hipotéticos de falla QA:
     *
     * - Permite guardar medicamentos duplicados.
     * - Permite precio negativo.
     * - Permite stock negativo.
     * - No encuentra medicamento existente.
     * - Eliminar devuelve error para registros válidos.
     * - Actualizar no persiste cambios.
     */
}