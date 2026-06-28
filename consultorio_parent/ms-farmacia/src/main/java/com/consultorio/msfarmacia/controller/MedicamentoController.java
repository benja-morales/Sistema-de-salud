package com.consultorio.msfarmacia.controller;

import java.util.List;

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

import com.consultorio.msfarmacia.model.Medicamento;
import com.consultorio.msfarmacia.service.MedicamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicamentos")
@Tag(name = "Farmacia", description = "Operaciones relacionadas con los medicamentos")
public class MedicamentoController {

    private final MedicamentoService service;

    public MedicamentoController(MedicamentoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary="Obtener todos los medicamentos", description="Obtiene una lista de todos los medicamentos")
    @ApiResponses(value = {
        @ApiResponse(responseCode= "200", description = "Lista entregada de manera exitosa", 
            content= @Content(mediaType= "application/json",
            schema= @Schema(implementation= Medicamento.class))),
        @ApiResponse(responseCode= "404", description= "No existen Medicamentos entregable")
    })
    public ResponseEntity<List<Medicamento>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener el medicamento con el ID", description="Obtiene un medicamento en base a un ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode= "200", description = "Medicamento encontrado", 
            content= @Content(mediaType= "application/json",
            schema= @Schema(implementation= Medicamento.class))),
        @ApiResponse(responseCode= "404", description= "No existe medicamento con ese ID")
    })
    public ResponseEntity<Medicamento> buscar(
        @Parameter(description="Id del medicamento", required= true) 
        @PathVariable Long id) {
            return ResponseEntity.ok(service.buscarId(id));
    }

    @PostMapping
    @Operation(summary="Almacenar un medicamento nuevo", description="Agrega medicamento a la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode= "200", description = "Medicamento agregado exitosamente", 
            content= @Content(mediaType= "application/json",
            schema= @Schema(implementation= Medicamento.class))),
        @ApiResponse(responseCode= "404", description= "No fue posible agregar medicamento")
    })
    public ResponseEntity<Medicamento> guardar(
            @Valid @RequestBody Medicamento medicamento) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(medicamento));
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar datos del medicamento con el ID", description="Actualiza medicamento ")
    @ApiResponses(value = {
        @ApiResponse(responseCode= "200", description = "Medicamento encontrado y actualizado", 
            content= @Content(mediaType= "application/json",
            schema= @Schema(implementation= Medicamento.class))),
        @ApiResponse(responseCode= "404", description= "No existe medicamento con ese ID")
    })
    public ResponseEntity<Medicamento> actualizar(
            @Parameter(description="Id del medicamento", required= true)
            @PathVariable Long id,
            @RequestBody Medicamento medicamento) {

        return ResponseEntity.ok(service.actualizar(id, medicamento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Eliminar datos del medicamento en base al ID", description="")
    @ApiResponses(value = {
        @ApiResponse(responseCode= "200", description = "Medicamento encontrado y eliminado", 
            content= @Content(mediaType= "application/json",
            schema= @Schema(implementation= Medicamento.class))),
        @ApiResponse(responseCode= "404", description= "No existe medicamento con ese ID")
    })
    public ResponseEntity<String> eliminar(
        @Parameter(description="Id del medicamento", required= true)
        @PathVariable Long id) {

            service.eliminar(id);

        return ResponseEntity.ok("Medicamento eliminado");
    }
}