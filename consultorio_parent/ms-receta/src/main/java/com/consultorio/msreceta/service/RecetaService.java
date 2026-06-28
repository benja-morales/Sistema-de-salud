package com.consultorio.msreceta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.consultorio.msreceta.client.FarmaciaClient;
import com.consultorio.msreceta.client.MedicoClient;
import com.consultorio.msreceta.client.PacienteClient;
import com.consultorio.msreceta.model.MedicamentoReceta;
import com.consultorio.msreceta.model.Receta;
import com.consultorio.msreceta.repository.RecetaRepository;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private MedicoClient medicoClient;

    @Autowired
    private FarmaciaClient farmaciaClient;

    // LISTAR TODAS
    public List<Receta> listar() {

        return recetaRepository.findAll();
    }

    // BUSCAR POR ID
    public Receta buscarPorId(@NonNull Long id) {

        return recetaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Receta no encontrada"));
    }

    // RECETAS POR PACIENTE
    public List<Receta> buscarPorPaciente(
            Long idPaciente) {

        return recetaRepository.findByIdPaciente(
                idPaciente);
    }

    // RECETAS POR ESPECIALISTA
    public List<Receta> buscarPorMedico(
            Long idMedico) {

        return recetaRepository
                .findByIdMedico(
                        idMedico);
    }

    // CREAR RECETA
    public Receta guardar(Receta receta) {

        // VALIDAR MEDICAMENTOS
        if (receta.getMedicamentos() == null
                || receta.getMedicamentos().isEmpty()) {

            throw new RuntimeException(
                    "La receta debe tener al menos un medicamento");
        }

        // VALIDAR PACIENTE
        if (pacienteClient.obtenerPaciente(
                receta.getIdPaciente()) == null) {

            throw new RuntimeException(
                    "Paciente no encontrado");
        }

        // VALIDAR ESPECIALISTA
        if (medicoClient.obtenerEspecialista(
                receta.getIdMedico()) == null) {

            throw new RuntimeException(
                    "Especialista no encontrado");
        }

        // VALIDAR MEDICAMENTOS EXISTENTES
        receta.getMedicamentos().forEach(
                medicamento -> {

            if (farmaciaClient.obtenerMedicamento(
                    medicamento.getIdMedicamento()) == null) {

                throw new RuntimeException(
                        "Medicamento no encontrado");
            }
        });

        return recetaRepository.save(receta);
    }

    // AGREGAR MEDICAMENTO
    public Receta agregarMedicamento(
            @NonNull Long recetaId,
            MedicamentoReceta medicamento) {

        Receta receta = buscarPorId(recetaId);

        // VALIDAR MEDICAMENTO
        if (farmaciaClient.obtenerMedicamento(
                medicamento.getIdMedicamento()) == null) {

            throw new RuntimeException(
                    "Medicamento no encontrado");
        }

        receta.getMedicamentos().add(medicamento);

        return recetaRepository.save(receta);
    }

    // ELIMINAR MEDICAMENTO
    public Receta eliminarMedicamento(
            @NonNull Long recetaId,
            Long medicamentoId) {

        Receta receta = buscarPorId(recetaId);

        receta.getMedicamentos().removeIf(
                medicamento ->
                        medicamentoId.equals(
                                medicamento.getId()));

        // VALIDAR MÍNIMO 1 MEDICAMENTO
        if (receta.getMedicamentos().isEmpty()) {

            throw new RuntimeException(
                    "La receta debe tener al menos un medicamento");
        }

        return recetaRepository.save(receta);
    }

    // ELIMINAR RECETA
    public void eliminar(@NonNull Long id) {

        Receta receta = buscarPorId(id);

        recetaRepository.delete(receta);
    }
}