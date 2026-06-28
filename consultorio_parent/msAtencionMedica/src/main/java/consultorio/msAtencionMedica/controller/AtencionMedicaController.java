package consultorio.msAtencionMedica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consultorio.msAtencionMedica.model.AtencionMedica;
import consultorio.msAtencionMedica.service.AtencionMedicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/atenciones")
@Tag(
    name = "Atenciones Médicas",
    description = "Operaciones relacionadas con las atenciones médicas"
)
public class AtencionMedicaController {

    @Autowired
    private AtencionMedicaService service;

    // llamar a todas las atenciones
    @GetMapping
    @Operation(
    summary = "Listar atenciones médicas",
    description = "Obtiene todas las atenciones médicas registradas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(
                    schema = @Schema(implementation = AtencionMedica.class)
                )
            )
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No existen atenciones registradas"
        )
    })
    public ResponseEntity<?> listar() {

        List<AtencionMedica> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    // registrar atencion
    @PostMapping
    @Operation(
    summary = "Registrar atención médica",
    description = "Registra una nueva atención médica"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Atención registrada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AtencionMedica.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<?> registrar(@RequestBody AtencionMedica atencion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(atencion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // asociar cita
    @PostMapping("/cita/{idCita}")
    @Operation(
    summary = "Asociar atención a una cita",
    description = "Asocia una atención médica a una cita existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Atención asociada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AtencionMedica.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error al asociar atención"
        )
    })
    public ResponseEntity<?> asociar(
            @Parameter(
            description = "ID de la cita",
            example = "8",
            required = true
            )
            @PathVariable Long idCita,
            @RequestBody AtencionMedica atencion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(service.asociarACita(idCita, atencion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // obtener por id
    @GetMapping("/{id}")
    @Operation(
    summary = "Obtener atención médica",
    description = "Obtiene una atención médica utilizando su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Atención encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AtencionMedica.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Atención no encontrada"
        )
    })
    public ResponseEntity<?> obtener(
        @Parameter(
        description = "ID de la atención médica",
        example = "1",
        required = true
        )
        @PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // agregar diagnostico
    @PutMapping("/{id}/diagnostico")
    @Operation(
    summary = "Agregar diagnóstico",
    description = "Actualiza el diagnóstico de una atención médica"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Diagnóstico actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AtencionMedica.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Atención no encontrada"
        )
    })
    public ResponseEntity<?> diagnostico(
        @Parameter(
        description = "ID de la atención médica",
        example = "1",
        required = true
        )
        @PathVariable Long id,
            @RequestBody AtencionMedica atencion) {
        try {
            return ResponseEntity.ok(service.agregarDiagnostico(id, atencion.getDiagnostico()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // agregar observaciones
    @PutMapping("/{id}/observaciones")
    @Operation(
    summary = "Agregar observaciones",
    description = "Actualiza las observaciones de una atención médica"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Observaciones actualizadas correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AtencionMedica.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Atención no encontrada"
        )
    })
    public ResponseEntity<?> observaciones(
        @Parameter(
        description = "ID de la atención médica",
        example = "1",
        required = true
        )
        @PathVariable Long id,
            @RequestBody AtencionMedica atencion) {

        try {
            return ResponseEntity.ok(service.agregarobservaciones(id, atencion.getObservaciones()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
