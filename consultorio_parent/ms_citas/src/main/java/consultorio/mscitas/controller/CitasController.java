package consultorio.mscitas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consultorio.mscitas.model.Citas;
import consultorio.mscitas.service.CitasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas", description = "Operaciones relacionadas con las citas médicas")
public class CitasController {
    @Autowired
    private CitasService service;

    // Crear cita
    @PostMapping
    @Operation(summary = "Crear cita", description = "Registra una nueva cita médica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cita creada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Citas.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> crear(@RequestBody Citas citas) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(citas));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // reagendar
    @PutMapping("/{id}/reagendar")
    @Operation(summary = "Reagendar cita", description = "Modifica fecha y hora de una cita existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita reagendada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Citas.class))),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<?> reagendar(

            @Parameter(description = "ID de la cita", example = "1", required = true) @PathVariable Long id,
            @RequestBody Citas cita) {
        try {
            return ResponseEntity.ok(service.reagendar(id, cita));
        } catch (RuntimeException e) {

            if (e.getMessage().contains("no encontrada")) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // cancelar
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar cita", description = "Cancela una cita médica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita cancelada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Citas.class))),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<?> cancelar(

            @Parameter(description = "ID de la cita", example = "1", required = true) @PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.cancelar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // cambiar estado
    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de cita", description = "Actualiza el estado de una cita médica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Citas.class))),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<?> cambiarEstado(
            @Parameter(description = "ID de la cita", example = "4", required = true) @PathVariable Long id,
            @RequestBody Citas cita) {
        try {
            return ResponseEntity.ok(service.cambiarEstado(id, cita.getEstado()));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrada")) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    // obtener por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cita por ID", description = "Obtiene una cita específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Citas.class))),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<?> obtener(
            @Parameter(description = "ID de la cita", example = "2", required = true) @PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // listar todas
    @GetMapping
    @Operation(summary = "Listar citas", description = "Obtiene todas las citas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Citas.class))))
    })
    public ResponseEntity<?> listar() {
        List<Citas> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // Buscar por paciente
    @GetMapping("/paciente/{id}")
    @Operation(summary = "Buscar citas por paciente", description = "Obtiene las citas asociadas a un paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Citas encontradas", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Citas.class))))
    })
    public ResponseEntity<?> porPaciente(
            @Parameter(description = "ID del paciente", example = "10", required = true) @PathVariable Long id) {
        List<Citas> lista = service.porPaciente(id);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // Buscar por médico
    @GetMapping("/medico/{id}")
    @Operation(summary = "Buscar citas por médico", description = "Obtiene las citas asociadas a un médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Citas encontradas", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Citas.class))))
    })
    public ResponseEntity<?> porMedico(
            @Parameter(description = "ID del médico", example = "5", required = true) @PathVariable Long id) {
        List<Citas> lista = service.porMedico(id);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // Buscar por fecha
    @GetMapping("/fecha/{fecha}")
    @Operation(summary = "Buscar citas por fecha", description = "Obtiene todas las citas registradas para una fecha específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Citas encontradas", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Citas.class))))
    })
    public ResponseEntity<?> porFecha(
            @Parameter(description = "Fecha de búsqueda", example = "21-06-2026", required = true) @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha) {
        List<Citas> lista = service.porFecha(fecha);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
}
