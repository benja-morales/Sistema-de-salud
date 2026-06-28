package com.consultorio.ms_examen.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.consultorio.ms_examen.modelo.Examen;

@Repository 
public interface ExamenRepository extends JpaRepository<Examen, Long>{
    List<Examen> findByIdPaciente(Long idPaciente);

    List<Examen> findByFechaResultado(LocalDate fechaResultado);
    
    List<Examen> findByEstado(String estado);

    @Query("SELECT e FROM Examen e WHERE FUNCTION('DATE', e.fechaSolicitud) = :fecha")
    List<Examen> buscarPorFecha(@Param("fecha") LocalDate fecha);

}
