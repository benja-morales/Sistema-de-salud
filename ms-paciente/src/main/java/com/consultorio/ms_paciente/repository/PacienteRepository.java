package com.consultorio.ms_paciente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.ms_paciente.modelo.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    //contrar el paciente por su rut 
    Optional<Paciente> findByRut(String rut);

    //contrar el paciente por su email
    Optional<Paciente> findByEmail(String email);

    //validaciones rut y dv antes de crear o actualizar
    boolean existsByRut(String rut);

    //buscar por apellidos
    List<Paciente> findByApellidosContainingIgnoreCase(String apellidos);
}
