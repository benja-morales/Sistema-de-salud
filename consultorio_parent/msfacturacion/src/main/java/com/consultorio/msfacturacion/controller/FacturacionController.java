package com.consultorio.msfacturacion.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.msfacturacion.modelo.Facturacion;
import com.consultorio.msfacturacion.service.FacturacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/facturas")
public class FacturacionController {

    @Autowired
    private FacturacionService facturaService;

    
    @PostMapping
    public ResponseEntity<Facturacion> crear(@Valid @RequestBody Facturacion factura) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.crearFactura(factura));
    }

    // Requerimiento: "Obtener factura por ID"
    @GetMapping("/{id}")
    public ResponseEntity<Facturacion> obtenerPorId(@PathVariable Long id) {
        return facturaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Requerimiento: "Facturas por paciente"
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Facturacion>> listarPorPaciente(@PathVariable Long idPaciente) {
        List<Facturacion> facturas = facturaService.listarPorPaciente(idPaciente);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    // Requerimiento: "Actualizar factura"
    @PutMapping("/{id}")
    public ResponseEntity<Facturacion> actualizar(@PathVariable Long id, @Valid @RequestBody Facturacion detallesFactura) {
        return facturaService.obtenerPorId(id)
                .map(facturaExistente -> {
                    // Actualizamos los campos necesarios
                    facturaExistente.setIdPaciente(detallesFactura.getIdPaciente());
                    facturaExistente.setTotal(detallesFactura.getTotal());
                    facturaExistente.setFechaEmision(detallesFactura.getFechaEmision());
                    facturaExistente.setDetalles(detallesFactura.getDetalles());
                    // Si tienes un campo estado, lo actualizas también
                    
                    Facturacion actualizada = facturaService.crearFactura(facturaExistente);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Requerimiento: "Facturas por fecha"
    // Ejemplo de uso: /api/facturas/fecha/2024-03-20
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Facturacion>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Facturacion> facturas = facturaService.listarPorFecha(fecha);
        return ResponseEntity.ok(facturas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }
}
