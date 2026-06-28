package com.consultorio.msreceta.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.consultorio.msreceta.client.FarmaciaClient;
import com.consultorio.msreceta.client.MedicoClient;
import com.consultorio.msreceta.client.PacienteClient;
import com.consultorio.msreceta.dto.MedicamentoDTO;
import com.consultorio.msreceta.dto.MedicoDTO;
import com.consultorio.msreceta.dto.PacienteDTO;
import com.consultorio.msreceta.model.MedicamentoReceta;
import com.consultorio.msreceta.model.Receta;
import com.consultorio.msreceta.repository.RecetaRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private PacienteClient pacienteClient;

    @Mock
    private MedicoClient medicoClient;

    @Mock
    private FarmaciaClient farmaciaClient;

    @InjectMocks
    private RecetaService recetaService;

    @Test
    @DisplayName("listar() debe retornar todas las recetas")
    void deberiaListarTodasLasRecetas() {

        when(recetaRepository.findAll())
                .thenReturn(List.of(new Receta(), new Receta()));

        List<Receta> resultado = recetaService.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(recetaRepository).findAll();
    }

    @Test
    @DisplayName("buscarPorId() debe retornar receta existente")
    void deberiaBuscarPorId() {

        Receta receta = new Receta();
        receta.setId(1L);

        when(recetaRepository.findById(1L))
                .thenReturn(Optional.of(receta));

        Receta resultado = recetaService.buscarPorId(1L);

        assertEquals(1L, resultado.getId());

        verify(recetaRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarPorId() debe lanzar excepción")
    void deberiaLanzarExcepcionSiNoExiste() {

        when(recetaRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> recetaService.buscarPorId(99L));

        assertEquals(
                "Receta no encontrada",
                ex.getMessage());
    }

    @Test
    @DisplayName("buscarPorPaciente()")
    void deberiaBuscarPorPaciente() {

        when(recetaRepository.findByIdPaciente(10L))
                .thenReturn(List.of(new Receta()));

        List<Receta> resultado =
                recetaService.buscarPorPaciente(10L);

        assertEquals(1, resultado.size());

        verify(recetaRepository)
                .findByIdPaciente(10L);
    }

    @Test
    @DisplayName("buscarPorMedico()")
    void deberiaBuscarPorMedico() {

        when(recetaRepository.findByIdMedico(5L))
                .thenReturn(List.of(new Receta()));

        List<Receta> resultado =
                recetaService.buscarPorMedico(5L);

        assertEquals(1, resultado.size());

        verify(recetaRepository)
                .findByIdMedico(5L);
    }

    @Test
    @DisplayName("guardar() debe guardar receta válida")
    void deberiaGuardarReceta() {

        MedicamentoReceta medicamento =
                new MedicamentoReceta();

        medicamento.setIdMedicamento(1L);
        medicamento.setNombreMedicamento("Paracetamol");
        medicamento.setCantidad(2);
        medicamento.setIndicaciones("Cada 8 horas");

        Receta receta = new Receta();
        receta.setIdPaciente(1L);
        receta.setIdMedico(2L);
        receta.setFecha(LocalDate.now());
        receta.setMedicamentos(List.of(medicamento));

        PacienteDTO paciente = new PacienteDTO();
        paciente.setId(1L);

        MedicoDTO medico = new MedicoDTO();
        medico.setIdEsp(2L);

        MedicamentoDTO medicamentoDTO =
                new MedicamentoDTO();
        medicamentoDTO.setId(1L);

        when(pacienteClient.obtenerPaciente(1L))
                .thenReturn(paciente);

        when(medicoClient.obtenerEspecialista(2L))
                .thenReturn(medico);

        when(farmaciaClient.obtenerMedicamento(1L))
                .thenReturn(medicamentoDTO);

        when(recetaRepository.save(receta))
                .thenReturn(receta);

        Receta resultado =
                recetaService.guardar(receta);

        assertNotNull(resultado);

        verify(recetaRepository).save(receta);
    }

    @Test
    @DisplayName("guardar() debe fallar sin medicamentos")
    void deberiaFallarSinMedicamentos() {

        Receta receta = new Receta();
        receta.setMedicamentos(new ArrayList<>());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> recetaService.guardar(receta));

        assertEquals(
                "La receta debe tener al menos un medicamento",
                ex.getMessage());
    }

    @Test
    @DisplayName("guardar() debe fallar si paciente no existe")
    void deberiaFallarSiPacienteNoExiste() {

        MedicamentoReceta medicamento =
                new MedicamentoReceta();

        medicamento.setIdMedicamento(1L);

        recetaBase(medicamento);

        Receta receta = recetaBase(medicamento);

        when(pacienteClient.obtenerPaciente(1L))
                .thenReturn(null);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> recetaService.guardar(receta));

        assertEquals(
                "Paciente no encontrado",
                ex.getMessage());
    }

    @Test
    @DisplayName("guardar() debe fallar si especialista no existe")
    void deberiaFallarSiEspecialistaNoExiste() {

        MedicamentoReceta medicamento =
                new MedicamentoReceta();

        medicamento.setIdMedicamento(1L);

        Receta receta = recetaBase(medicamento);

        PacienteDTO paciente = new PacienteDTO();
        paciente.setId(1L);

        when(pacienteClient.obtenerPaciente(1L))
                .thenReturn(paciente);

        when(medicoClient.obtenerEspecialista(2L))
                .thenReturn(null);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> recetaService.guardar(receta));

        assertEquals(
                "Especialista no encontrado",
                ex.getMessage());
    }

    @Test
    @DisplayName("guardar() debe fallar si medicamento no existe")
    void deberiaFallarSiMedicamentoNoExiste() {

        MedicamentoReceta medicamento =
                new MedicamentoReceta();

        medicamento.setIdMedicamento(1L);

        Receta receta = recetaBase(medicamento);

        PacienteDTO paciente = new PacienteDTO();
        paciente.setId(1L);

        MedicoDTO medico = new MedicoDTO();
        medico.setIdEsp(2L);

        when(pacienteClient.obtenerPaciente(1L))
                .thenReturn(paciente);

        when(medicoClient.obtenerEspecialista(2L))
                .thenReturn(medico);

        when(farmaciaClient.obtenerMedicamento(1L))
                .thenReturn(null);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> recetaService.guardar(receta));

        assertEquals(
                "Medicamento no encontrado",
                ex.getMessage());
    }

    private Receta recetaBase(
            MedicamentoReceta medicamento) {

        Receta receta = new Receta();

        receta.setIdPaciente(1L);
        receta.setIdMedico(2L);
        receta.setFecha(LocalDate.now());
        receta.setMedicamentos(
                List.of(medicamento));

        return receta;
    }
}