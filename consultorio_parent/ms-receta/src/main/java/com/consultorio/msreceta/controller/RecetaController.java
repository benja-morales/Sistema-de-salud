package com.consultorio.msreceta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.msreceta.model.MedicamentoReceta;
import com.consultorio.msreceta.model.Receta;
import com.consultorio.msreceta.service.RecetaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recetas")
@Tag(name = "Recetas", description = "Operaciones relacionadas con recetas médicas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @GetMapping
    @Operation(summary = "Obtener todas las recetas", description = "Obtiene una lista de todas las recetas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Receta.class))),
            @ApiResponse(responseCode = "404", description = "No existen recetas registradas")
    })
    public ResponseEntity<List<Receta>> listar() {

        return ResponseEntity.ok(
                recetaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar receta por ID", description = "Obtiene una receta utilizando su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Receta.class))),
            @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    public ResponseEntity<Receta> buscarPorId(
            @Parameter(description = "ID de la receta", required = true)
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recetaService.buscarPorId(id));
    }

    @GetMapping("/paciente/{idPaciente}")
    @Operation(summary = "Buscar recetas por paciente", description = "Obtiene todas las recetas asociadas a un paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas"),
            @ApiResponse(responseCode = "404", description = "No existen recetas para el paciente")
    })
    public ResponseEntity<List<Receta>> buscarPorPaciente(
            @Parameter(description = "ID del paciente", required = true)
            @PathVariable Long idPaciente) {

        return ResponseEntity.ok(
                recetaService.buscarPorPaciente(idPaciente));
    }

    @GetMapping("/medicos/{idMedico}")
    @Operation(summary = "Buscar recetas por médico", description = "Obtiene todas las recetas emitidas por un médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas"),
            @ApiResponse(responseCode = "404", description = "No existen recetas para el médico")
    })
    public ResponseEntity<List<Receta>> buscarPorMedico(
            @Parameter(description = "ID del médico", required = true)
            @PathVariable Long idMedico) {

        return ResponseEntity.ok(
                recetaService.buscarPorMedico(idMedico));
    }

    @PostMapping
    @Operation(summary = "Crear receta", description = "Registra una nueva receta médica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receta creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Receta.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Receta> guardar(
            @Valid @RequestBody Receta receta) {

        return ResponseEntity.ok(
                recetaService.guardar(receta));
    }

    @PostMapping("/{id}/medicamentos")
    @Operation(summary = "Agregar medicamento a receta", description = "Añade un medicamento a una receta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento agregado correctamente"),
            @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    public ResponseEntity<Receta> agregarMedicamento(
            @Parameter(description = "ID de la receta", required = true)
            @PathVariable Long id,
            @Valid @RequestBody MedicamentoReceta medicamento) {

        return ResponseEntity.ok(
                recetaService.agregarMedicamento(
                        id,
                        medicamento));
    }

    @DeleteMapping("/{id}/medicamentos/{medicamentoId}")
    @Operation(summary = "Eliminar medicamento de receta", description = "Elimina un medicamento específico de una receta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Receta o medicamento no encontrado")
    })
    public ResponseEntity<Receta> eliminarMedicamento(
            @Parameter(description = "ID de la receta", required = true)
            @PathVariable Long id,

            @Parameter(description = "ID del medicamento en la receta", required = true)
            @PathVariable Long medicamentoId) {

        return ResponseEntity.ok(
                recetaService.eliminarMedicamento(
                        id,
                        medicamentoId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar receta", description = "Elimina una receta médica existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID de la receta", required = true)
            @PathVariable Long id) {

        recetaService.eliminar(id);

        return ResponseEntity.ok(
                "Receta eliminada correctamente");
    }
}