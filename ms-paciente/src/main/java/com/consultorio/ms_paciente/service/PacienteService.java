package com.consultorio.ms_paciente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.consultorio.ms_paciente.modelo.Paciente;
import com.consultorio.ms_paciente.repository.PacienteRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;


@Service
@Transactional //para que se ejecute todo o nada, si falla algo se revierte
public class PacienteService {
    
    @Autowired //inyeccion de dependencias
    private PacienteRepository pacienteRepository;

    // LISTAR TODOS
    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    // BUSCAR POR ID
    public Paciente findById(@NonNull Long id) {
        return pacienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    // GUARDAR
    public Paciente save(Paciente paciente) {

        // VALIDAR RUT REPETIDo
        if (pacienteRepository.existsByRut(paciente.getRut())) {
            // Si el paciente ya existe, lanzar una excepción
            throw new jakarta.persistence.EntityExistsException
            ("El paciente ya se encuentra en nuestros registros.");
        }
        paciente.setActivo(true);
        return pacienteRepository.save(paciente);
    }

    // ACTUALIZAR
    public Paciente update(@NonNull Long id, Paciente paciente) {
    Paciente actual = findById(id); 

    // 1. Validar y actualizar RUT
    if (paciente.getRut() != null && !actual.getRut().equals(paciente.getRut())) {
        if (pacienteRepository.existsByRut(paciente.getRut())) {
            throw new EntityExistsException("El RUT " + paciente.getRut() + " ya pertenece a otro paciente.");
        }
        actual.setRut(paciente.getRut());
    }

    // 2. ACTUALIZAR EL DV
    if (paciente.getDv() != null) {
        actual.setDv(paciente.getDv());
    }

    // 3. Resto de campos
    if (paciente.getNombre() != null) actual.setNombre(paciente.getNombre());
    if (paciente.getApellidos() != null) actual.setApellidos(paciente.getApellidos());
    if (paciente.getEmail() != null) actual.setEmail(paciente.getEmail());
    if (paciente.getTelefono() != null) actual.setTelefono(paciente.getTelefono());
    if (paciente.getActivo() != null) actual.setActivo(paciente.getActivo());

    return pacienteRepository.save(actual);
    }

    // ELIMINAR
    public void delete(@NonNull Long id) {
    Paciente paciente = findById(id); // Verifica si existe
    pacienteRepository.delete(paciente); // Esto ejecuta un "DELETE FROM pacientes WHERE id = ..."
}

    // BUSCAR POR RUT
    public Paciente findByRut(String rut) {
        // Limpia el RUT de puntos, guiones y espacios
        String rutLimpio = rut.trim()
                            .replace(".", "")
                            .replace("-", "")
                            .toUpperCase(); // Asegura coincidencia con el DV 'K'

        // Consulta al repositorio con manejo de excepción personalizada
        return pacienteRepository.findByRut(rutLimpio).get();
        }

    // BUSCAR POR APELLIDOS
    public List<Paciente> findByApellidos(String apellidos) {
        return pacienteRepository.findByApellidosContainingIgnoreCase(apellidos);
    }
}
