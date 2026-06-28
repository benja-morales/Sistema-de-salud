package com.consultorio.ms_examen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.consultorio.ms_examen.modelo.Examen;
import com.consultorio.ms_examen.repository.ExamenRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ExamenServiceTest {

    @Mock
    private ExamenRepository examenRepository;

    @InjectMocks
    private ExamenService examenService;

    private Examen examen;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        examen = new Examen();
        examen.setIdExamen(1L);
        examen.setTipoExamen("Radiografía");
        examen.setIdPaciente(1L);
        examen.setEstado("PENDIENTE");
        examen.setResultadoTexto("Sin observaciones");
        examen.setFechaResultado(LocalDate.now());
    }

    @Test
    void debeBuscarExamenPorId() {

        // Arrange
        when(examenRepository.findById(1L))
                .thenReturn(Optional.of(examen));

        // Act
        Examen resultado = examenService.buscar(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Radiografía", resultado.getTipoExamen());
    }

    @Test
    void debeGuardarExamenCorrectamente() {

        // Arrange
        when(examenRepository.save(any(Examen.class)))
                .thenReturn(examen);

        // Act
        Examen resultado = examenService.guardar(examen);

        // Assert
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());

        verify(examenRepository, times(1))
                .save(examen);
    }

    @Test
    void debeLanzarExcepcionSiExamenNoExiste() {

        // Arrange
        when(examenRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> examenService.buscar(99L));

        assertEquals(
                "Examen no encontrado",
                exception.getMessage());
    }
}