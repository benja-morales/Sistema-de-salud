package com.consultorio.msreceta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.msreceta.assemblers.RecetaModelAssembler;
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
@RequestMapping("/api/v2/recetas")
@Tag(
    name = "Recetas HATEOAS",
    description = "Operaciones relacionadas con recetas médicas utilizando HATEOAS"
)
public class RecetaControllerV2 {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private RecetaModelAssembler assembler;

    @GetMapping
    @Operation(
        summary = "Obtener todas las recetas",
        description = "Obtiene una lista de recetas con enlaces HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "No existen recetas registradas")
    })
    public CollectionModel<EntityModel<Receta>> listar() {

        List<EntityModel<Receta>> recetas =
                recetaService.listar()
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        return CollectionModel.of(recetas);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar receta por ID",
        description = "Obtiene una receta utilizando su identificador con enlaces HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta encontrada",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Receta.class))),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    public EntityModel<Receta> buscarPorId(
            @Parameter(description = "ID de la receta", required = true)
            @PathVariable Long id) {

        return assembler.toModel(
                recetaService.buscarPorId(id));
    }

    @GetMapping("/paciente/{idPaciente}")
    @Operation(
        summary = "Buscar recetas por paciente",
        description = "Obtiene todas las recetas asociadas a un paciente"
    )
    public CollectionModel<EntityModel<Receta>> buscarPorPaciente(
            @PathVariable Long idPaciente) {

        List<EntityModel<Receta>> recetas =
                recetaService.buscarPorPaciente(idPaciente)
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        return CollectionModel.of(recetas);
    }

    @GetMapping("/medicos/{idMedico}")
    @Operation(
        summary = "Buscar recetas por médico",
        description = "Obtiene todas las recetas emitidas por un médico"
    )
    public CollectionModel<EntityModel<Receta>> buscarPorMedico(
            @PathVariable Long idMedico) {

        List<EntityModel<Receta>> recetas =
                recetaService.buscarPorMedico(idMedico)
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        return CollectionModel.of(recetas);
    }

    @PostMapping
    @Operation(
        summary = "Crear receta",
        description = "Registra una nueva receta médica"
    )
    public ResponseEntity<EntityModel<Receta>> guardar(
            @Valid @RequestBody Receta receta) {

        Receta nueva = recetaService.guardar(receta);

        return ResponseEntity.ok(
                assembler.toModel(nueva));
    }

    @PostMapping("/{id}/medicamentos")
    @Operation(
        summary = "Agregar medicamento a receta",
        description = "Añade un medicamento a una receta existente"
    )
    public ResponseEntity<EntityModel<Receta>> agregarMedicamento(
            @PathVariable Long id,
            @Valid @RequestBody MedicamentoReceta medicamento) {

        return ResponseEntity.ok(
                assembler.toModel(
                        recetaService.agregarMedicamento(
                                id,
                                medicamento)));
    }

    @DeleteMapping("/{id}/medicamentos/{medicamentoId}")
    @Operation(
        summary = "Eliminar medicamento de receta",
        description = "Elimina un medicamento específico de una receta"
    )
    public ResponseEntity<EntityModel<Receta>> eliminarMedicamento(
            @PathVariable Long id,
            @PathVariable Long medicamentoId) {

        return ResponseEntity.ok(
                assembler.toModel(
                        recetaService.eliminarMedicamento(
                                id,
                                medicamentoId)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar receta",
        description = "Elimina una receta médica existente"
    )
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        recetaService.eliminar(id);

        return ResponseEntity.ok(
                "Receta eliminada correctamente");
    }
}