package com.consultorio.msreceta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.msreceta.model.Receta;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findByIdPaciente(Long idPaciente);

    List<Receta> findByIdMedico(Long idMedico);
}