package com.consultorio.msfacturacion.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultorio.msfacturacion.modelo.Facturacion;
import com.consultorio.msfacturacion.repository.FacturacionRepository;

@Service
public class FacturacionService {

    @Autowired
    private FacturacionRepository facturaRepository;

    @Transactional
    public Facturacion crearFactura(Facturacion factura) {
        // El requerimiento dice "Calcular total automáticamente"
        // Lo aseguramos llamando al método que definimos en el modelo
        factura.getDetalles().forEach(detalle -> detalle.setFactura(factura));
        factura.calcularTotal(); 
        return facturaRepository.save(factura);
    }

    public List<Facturacion> listarPorPaciente(Long idPaciente) {
        return facturaRepository.findByIdPaciente(idPaciente);
    }

    // Método para guardar cambios (sirve para Crear y Actualizar)
    //public Facturacion actualizarFactura(Facturacion factura) {
        // Al usar .save(), Spring Data JPA detecta que el objeto YA TIENE un ID
        // y en lugar de crear uno nuevo, hace un UPDATE en la base de datos.
        //return facturaRepository.save(factura);
    //}

    public Facturacion actualizarFactura(Long id, Facturacion facturaData) {
    return facturaRepository.findById(id).map(facturaExistente -> {
        // 1. Actualizar datos básicos
        facturaExistente.setIdPaciente(facturaData.getIdPaciente());
        facturaExistente.setTotal(facturaData.getTotal());
        facturaExistente.setDetalles(facturaData.getDetalles());
        facturaExistente.setFechaEmision(facturaData.getFechaEmision());

        // 2. TRUCO PARA LA COLECCIÓN: Limpiar y re-agregar
        // No hagas: facturaExistente.setDetalles(facturaData.getDetalles()) -> ERROR
        if (facturaExistente.getDetalles() != null && facturaData.getDetalles() != null) {
            facturaExistente.getDetalles().clear(); // Vaciamos la lista original
            facturaExistente.getDetalles().addAll(facturaData.getDetalles()); // Agregamos los nuevos
            
            // 3. Vincular cada detalle con su padre (si es necesario)
            facturaExistente.getDetalles().forEach(detalle -> detalle.setFactura(facturaExistente));
        }

        return facturaRepository.save(facturaExistente);
    }).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
}

    public List<Facturacion> listarPorFecha(LocalDate fecha) {
        // Convertimos LocalDate a rango de LocalDateTime (00:00 a 23:59)
        return facturaRepository.findByFechaEmisionBetween(
            fecha.atStartOfDay(), 
            fecha.atTime(LocalTime.MAX)
        );
    }

    public Optional<Facturacion> obtenerPorId(Long id) {
        return facturaRepository.findById(id);
    }

    @Transactional
    public void eliminarFactura(Long id) {
        facturaRepository.deleteById(id);
    }
}
