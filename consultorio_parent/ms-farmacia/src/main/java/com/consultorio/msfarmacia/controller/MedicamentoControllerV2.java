package com.consultorio.msfarmacia.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import com.consultorio.msfarmacia.assemblers.FarmaciaModelAssembler;
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
@RequestMapping("/api/v2/medicamentos")
@Tag(
    name = "Farmacia HATEOAS",
    description = "Operaciones relacionadas con los medicamentos utilizando HATEOAS"
)
public class MedicamentoControllerV2 {

    private final MedicamentoService service;
    private final FarmaciaModelAssembler assembler;

    public MedicamentoControllerV2(
            MedicamentoService service,
            FarmaciaModelAssembler assembler) {

        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los medicamentos",
        description = "Obtiene una lista de todos los medicamentos con enlaces HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista entregada de manera exitosa",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Medicamento.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No existen medicamentos entregables"
        )
    })
    public CollectionModel<EntityModel<Medicamento>> listar() {

        List<EntityModel<Medicamento>> medicamentos =
                service.listar()
                       .stream()
                       .map(assembler::toModel)
                       .toList();

        return CollectionModel.of(medicamentos);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener el medicamento con el ID",
        description = "Obtiene un medicamento en base a un ID con enlaces HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Medicamento encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Medicamento.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No existe medicamento con ese ID"
        )
    })
    public EntityModel<Medicamento> buscar(
            @Parameter(
                description = "Id del medicamento",
                required = true
            )
            @PathVariable Long id) {

        Medicamento medicamento = service.buscarId(id);

        return assembler.toModel(medicamento);
    }

    @PostMapping
    @Operation(
        summary = "Almacenar un medicamento nuevo",
        description = "Agrega medicamento a la base de datos y retorna enlaces HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Medicamento agregado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Medicamento.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "No fue posible agregar medicamento"
        )
    })
    public ResponseEntity<EntityModel<Medicamento>> guardar(
            @Valid @RequestBody Medicamento medicamento) {

        Medicamento nuevo = service.guardar(medicamento);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(nuevo));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar datos del medicamento con el ID",
        description = "Actualiza un medicamento y retorna enlaces HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Medicamento encontrado y actualizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Medicamento.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No existe medicamento con ese ID"
        )
    })
    public EntityModel<Medicamento> actualizar(
            @Parameter(
                description = "Id del medicamento",
                required = true
            )
            @PathVariable Long id,
            @RequestBody Medicamento medicamento) {

        Medicamento actualizado =
                service.actualizar(id, medicamento);

        return assembler.toModel(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar datos del medicamento en base al ID",
        description = "Elimina un medicamento existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Medicamento encontrado y eliminado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No existe medicamento con ese ID"
        )
    })
    public ResponseEntity<String> eliminar(
            @Parameter(
                description = "Id del medicamento",
                required = true
            )
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok("Medicamento eliminado");
    }
}