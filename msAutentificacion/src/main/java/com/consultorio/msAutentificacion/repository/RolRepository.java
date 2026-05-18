package com.consultorio.msAutentificacion.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.msAutentificacion.modelo.ERol;
import com.consultorio.msAutentificacion.modelo.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    // Busca el rol específico (ADMIN, MEDICO o PACIENTE)
    Optional<Rol> findByNombre(ERol nombre);
}