package com.consultorio.msnotificaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.msnotificaciones.model.Notificacion;
import com.consultorio.msnotificaciones.service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(
    name = "Notificaciones",
    description = "Operaciones relacionadas con las notificaciones del sistema"
)
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping
    @Operation(
        summary = "Obtener todas las notificaciones",
        description = "Obtiene una lista completa de notificaciones registradas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Notificacion.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No existen notificaciones registradas"
        )
    })
    public ResponseEntity<List<Notificacion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar notificación por ID",
        description = "Obtiene una notificación específica mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Notificación encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Notificacion.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada"
        )
    })
    public ResponseEntity<Notificacion> buscarPorId(
            @Parameter(description = "ID de la notificación", required = true)
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(
        summary = "Crear notificación",
        description = "Registra una nueva notificación en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Notificación creada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Notificacion.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<Notificacion> guardar(
            @Valid @RequestBody Notificacion notificacion) {

        return ResponseEntity.ok(service.guardar(notificacion));
    }

    @PutMapping("/{id}/leer")
    @Operation(
        summary = "Marcar notificación como leída",
        description = "Actualiza el estado de una notificación a leída"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Notificación actualizada correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada"
        )
    })
    public ResponseEntity<Notificacion> marcarComoLeida(
            @Parameter(description = "ID de la notificación", required = true)
            @PathVariable Long id) {

        return ResponseEntity.ok(service.marcarComoLeida(id));
    }

    @GetMapping("/modulo/{modulo}")
    @Operation(
        summary = "Buscar notificaciones por módulo",
        description = "Obtiene todas las notificaciones pertenecientes a un módulo específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificaciones encontradas"),
        @ApiResponse(responseCode = "404", description = "No existen notificaciones para el módulo indicado")
    })
    public ResponseEntity<List<Notificacion>> buscarPorModulo(
            @Parameter(description = "Nombre del módulo", required = true)
            @PathVariable String modulo) {

        return ResponseEntity.ok(service.buscarPorModulo(modulo));
    }

    @GetMapping("/noleidas")
    @Operation(
        summary = "Obtener notificaciones no leídas",
        description = "Retorna todas las notificaciones pendientes de lectura"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    public ResponseEntity<List<Notificacion>> buscarNoLeidas() {

        return ResponseEntity.ok(service.buscarNoLeidas());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar notificación",
        description = "Elimina una notificación existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notificación eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la notificación", required = true)
            @PathVariable Long id) {

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/citas")
    @Operation(
        summary = "Obtener notificaciones de citas",
        description = "Obtiene mensajes relacionados con citas médicas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    public ResponseEntity<List<String>> obtenerNotificacionesCitas() {

        return ResponseEntity.ok(service.obtenerNotificacionesCitas());
    }

    @GetMapping("/recetas")
    @Operation(
        summary = "Obtener notificaciones de recetas",
        description = "Obtiene mensajes relacionados con recetas médicas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    public ResponseEntity<List<String>> obtenerNotificacionesRecetas() {

        return ResponseEntity.ok(service.obtenerNotificacionesRecetas());
    }

    @GetMapping("/examenes")
    @Operation(
        summary = "Obtener notificaciones de exámenes",
        description = "Obtiene mensajes relacionados con exámenes médicos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    public ResponseEntity<List<String>> obtenerNotificacionesExamenes() {

        return ResponseEntity.ok(service.obtenerNotificacionesExamenes());
    }
}