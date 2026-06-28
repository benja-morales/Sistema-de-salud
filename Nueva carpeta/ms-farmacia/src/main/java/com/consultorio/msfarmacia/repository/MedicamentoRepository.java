package com.consultorio.msfarmacia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultorio.msfarmacia.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    Optional<Medicamento> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}