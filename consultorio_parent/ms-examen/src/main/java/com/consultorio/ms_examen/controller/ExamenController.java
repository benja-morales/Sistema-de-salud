package com.consultorio.ms_examen.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.ms_examen.modelo.Examen;
import com.consultorio.ms_examen.service.ExamenService;

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
@RequestMapping("/api/examenes")
@Tag(name = "Examenes", description = "Operaciones relacionadas con las examenes")
@RequiredArgsConstructor
public class ExamenController {

    
    private final ExamenService examenService;

    //LISTAR TODOS LOS EXAMENES
    @GetMapping
    @Operation(summary = "Obtener todas los examenes", description = "Obtiene una lista de todas los examenes")
    @ApiResponse(
        responseCode = "200",
        description = "Operación exitosa",
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Examen.class)))
    )
    public List<Examen> listar(){
        return examenService.listar();
    }

    //BUSCAR POR ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener todas los examenes por ID", description = "Buscar examenes por id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Examen encontrado con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class))
        ),
        @ApiResponse(responseCode = "404", description = "Examen no encontrado")
    })
    public Examen buscar(
        @Parameter(description = "ID del examen a consultar", required = true, example = "1")
        @PathVariable Long id){
        return examenService.buscar(id);
    }
    //GUARDAR EXAMEN
    @PostMapping
    @Operation(summary = "Agrega un nuevo examen", description = "Registra una nueva orden de examen médico en el sistema")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Examen creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de solicitud inválidos",
            content = @Content
        )
    })
    public Examen guardar(
        @Valid 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del examen médico a registrar", 
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class))
        )
        @RequestBody Examen examen) {
        return examenService.guardar(examen);
    }
    //ELIMIAR EXAMEN POR ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un examen", description = "Remueve un examen del sistema de forma permanente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Examen eliminado con éxito",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Examen no encontrado",
            content = @Content
        )
    })
    public void eliminar(
        @Parameter(description = "ID del examen a eliminar", required = true, example = "1")
        @PathVariable Long id) {
        examenService.eliminar(id);
    }

    //EXAMEN POR PACIENTE
    @GetMapping("/paciente/{idPaciente}")
    @Operation(summary = "Buscar exámenes por paciente", description = "Obtiene la lista de exámenes asociados a un paciente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
            description = "Operación exitosa",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Examen.class)))
        ),
        @ApiResponse(responseCode = "404", 
            description = "Paciente no encontrado",
            content = @Content
        )
    })
    public List<Examen> buscarPorPaciente(
        @Parameter(description = "ID del paciente", required = true, example = "5") 
        @PathVariable long idPaciente){
        return examenService.buscarPorPaciente(idPaciente);
    }

    //EXAMEN POR FECHA
    @GetMapping("/fecha")
    @Operation(summary = "Buscar exámenes por fecha", description = "Obtiene una lista de exámenes programados o realizados en una fecha determinada (YYYY-MM-DD)")
    @ApiResponse(responseCode = "200",
         description = "Operación exitosa",
         content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Examen.class)))
        )
    public List<Examen> buscarPorFecha(
        @Parameter(description = "Fecha de los exámenes en formato YYYY-MM-DD", required = true, example = "2026-06-10")
        @RequestParam LocalDate fecha){
        return examenService.buscarPorFecha(fecha);    
    }

    //CAMBIO DE ESTADO
    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado del examen", description = "Modifica la etapa actual del examen (ej: PENDIENTE, EN_PROCESO, COMPLETADO) enviando el nuevo estado en el cuerpo de la petición")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
            description = "Estado actualizado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class))
        ),
        @ApiResponse(responseCode = "404", 
            description = "Examen no encontrado",
            content = @Content
        )
    })
    public Examen cambiarEstado(
        @Parameter(description = "ID del examen a modificar", required = true, example = "1")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Mapa que contiene el nuevo estado del examen (ej: {'estado': 'COMPLETADO'})", 
            required = true,
            content = @Content(mediaType = "application/json")
        )
        @PathVariable Long id,
        @RequestBody Map<String, String> body) {
        return examenService.cambiarEstado(id, body.get("estado"));
    }
}
