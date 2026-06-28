package com.consultorio.msfacturacion.repository;


import com.consultorio.msfacturacion.modelo.Facturacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {

    // Requerimiento: Facturas por paciente
    List<Facturacion> findByIdPaciente(Long idPaciente);

    // Requerimiento: Facturas por fecha
    // Usamos Between para capturar todo el rango del día si es necesario
    List<Facturacion> findByFechaEmisionBetween(LocalDateTime inicio, LocalDateTime fin);
}
