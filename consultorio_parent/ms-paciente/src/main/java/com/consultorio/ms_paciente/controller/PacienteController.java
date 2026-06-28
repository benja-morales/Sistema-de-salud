package com.consultorio.ms_paciente.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.ms_paciente.modelo.Paciente;
import com.consultorio.ms_paciente.service.PacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Operaciones relacionadas con la gestión de pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    @GetMapping
    @Operation(summary = "Obtener todos los pacientes", description = "Obtiene una lista de todos los pacientes registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida con éxito",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Paciente.class)))
    })
    public List<Paciente> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un paciente por su ID", description = "Devuelve los datos de un único paciente pasando su ID numérico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente encontrado con éxito",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Paciente.class))),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Paciente> buscarPorId( 
        @Parameter(description = "ID numérico del paciente a buscar", required = true)        
        @PathVariable @NonNull Long id){
            // AGREGUE EL PAREMETER
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar un paciente por su RUN", description = "Permite buscar a un paciente en la base de datos utilizando su RUN único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente encontrado con éxito",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Paciente.class))),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Paciente> buscarPorRut(
        @Parameter(description = "RUT del paciente a buscar (sin puntos ni dígito verificador)", required = true)
        @PathVariable String rut) {
            //AGREGUE EL PAREMETER
        return ResponseEntity.ok(service.findByRut(rut));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo paciente", description = "Crea un nuevo registro de paciente en el sistema y su base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Paciente registrado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Paciente.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes")
    })
    public ResponseEntity<Paciente> crear(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos completos del nuevo paciente a registrar",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Paciente.class),
                examples = @ExampleObject(
                    name = "Ejemplo Registro Paciente",
                    value = "{\"rut\": \"12345678\", \"dv\": \"9\", \"nombre\": \"Juan Carlos\", \"apellidos\": \"Pérez Gómez\", \"email\": \"juan.perez@email.com\", \"telefono\": \"+56912345678\", \"activo\": true}"
                )
            )
        )            
        @RequestBody @Valid Paciente paciente) {
        return new ResponseEntity<>(service.save(paciente), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un paciente existente", description = "Modifica los atributos de un paciente ya registrado identificándolo por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Paciente.class))),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Paciente> actualizar(
        @Parameter(description = "ID numérico del paciente a modificar", required = true)
        @PathVariable @NonNull Long id, 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Campos del paciente a actualizar",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Paciente.class),
                examples = @ExampleObject(
                    name = "Ejemplo Modificación Paciente",
                    value = "{\"rut\": \"12345678\", \"dv\": \"9\", \"nombre\": \"Juan Carlos\", \"apellidos\": \"Pérez Concha\", \"email\": \"juan.nuevo@email.com\", \"telefono\": \"+56987654321\", \"activo\": true}"
                )
            )
        )        
        @RequestBody Paciente paciente) {
        // Usamos 'service.update' que es el nombre que pusiste en tu lógica
        return ResponseEntity.ok(service.update(id, paciente));
    }

    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un paciente", description = "Elimina físicamente o de forma lógica el registro de un paciente mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Paciente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID numérico del paciente a eliminar", required = true)
        @PathVariable @Valid @NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
