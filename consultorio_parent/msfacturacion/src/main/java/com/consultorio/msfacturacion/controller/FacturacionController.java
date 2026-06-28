package com.consultorio.msfacturacion.controller;


import java.time.LocalDate;
import java.util.List;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/facturas")
@Tag(name = "Facturacion", description = "Operaciones relacionadas con el procesamiento de facturas y cobros")
@RequiredArgsConstructor
public class FacturacionController {

    
    private final FacturacionService facturaService;

    // CREAR FACTURA
    @PostMapping
    @Operation(summary = "Crear una nueva factura", description = "Registra una nueva factura médica en el sistema")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Factura creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Facturacion.class))
        ),
        @ApiResponse(responseCode = "400", description = "Datos de solicitud inválidos", content = @Content)
    })
    public ResponseEntity<Facturacion> crear(
        @Valid 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos detallados de la factura a generar", 
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Facturacion.class))
        )
        @RequestBody Facturacion factura) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.crearFactura(factura));
    }

    // OBTENER FACTURA POR ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener factura por ID", description = "Busca los datos de una factura específica utilizando su identificador único")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Factura encontrada con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Facturacion.class))
        ),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada", content = @Content)
    })
    public ResponseEntity<Facturacion> obtenerPorId(
        @Parameter(description = "ID único de la factura a consultar", required = true, example = "105")
        @PathVariable Long id) {
        return facturaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // FACTURA POR ID DEL PACIENTE
    @GetMapping("/paciente/{idPaciente}")
    @Operation(summary = "Obtener facturas por paciente", description = "Obtiene una lista de todas las facturas asociadas a un paciente mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Operación exitosa",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Facturacion.class)))
        ),
        @ApiResponse(responseCode = "24", description = "El paciente no registra facturas (No Content)", content = @Content)
    })
    public ResponseEntity<List<Facturacion>> listarPorPaciente(
        @Parameter(description = "ID del paciente para filtrar el historial de facturas", required = true, example = "45")
        @PathVariable Long idPaciente) {
        List<Facturacion> facturas = facturaService.listarPorPaciente(idPaciente);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    // ACTUALIZAR FACTURA
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar factura existente", description = "Modifica los campos editables de una factura mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Factura actualizada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Facturacion.class))
        ),
        @ApiResponse(responseCode = "404", description = "No se encontró la factura a modificar", content = @Content)
    })
    public ResponseEntity<Facturacion> actualizar(
        @Parameter(description = "ID de la factura que se desea modificar", required = true, example = "105")
        @PathVariable Long id, 
        @Valid 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Nuevos datos de la factura", 
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Facturacion.class))
        )
        @RequestBody Facturacion detallesFactura) {
        
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

    // FACTURA POR FECHA
    
    @GetMapping("/fecha/{fecha}")
    @Operation(summary = "Buscar facturas por fecha", description = "Obtiene un listado de las facturas emitidas en una fecha específica (YYYY-MM-DD)")
    @ApiResponse(
        responseCode = "200", 
        description = "Operación exitosa",
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Facturacion.class)))
    )
    public ResponseEntity<List<Facturacion>> listarPorFecha(
            @Parameter(description = "Fecha de emisión en formato YYYY-MM-DD", required = true, example = "2026-06-10")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Facturacion> facturas = facturaService.listarPorFecha(fecha);
        return ResponseEntity.ok(facturas);
    }

    // ELIMINAR FACTURA

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una factura", description = "Remueve permanentemente el registro de una factura en el sistema usando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Factura removida con éxito (No Content)", content = @Content),
        @ApiResponse(responseCode = "404", description = "La factura solicitada no existe", content = @Content)
    })
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID de la factura a eliminar", required = true, example = "105")
        @PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }
}
