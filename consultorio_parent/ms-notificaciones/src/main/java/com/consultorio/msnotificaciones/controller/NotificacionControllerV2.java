package com.consultorio.msnotificaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.msnotificaciones.assemblers.NotificacionModelAssembler;
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
@RequestMapping("/api/v2/notificaciones")
@Tag(
    name = "Notificaciones HATEOAS",
    description = "Operaciones relacionadas con las notificaciones utilizando HATEOAS"
)
public class NotificacionControllerV2 {

    @Autowired
    private NotificacionService service;

    @Autowired
    private NotificacionModelAssembler assembler;

    @GetMapping
    @Operation(
        summary = "Obtener todas las notificaciones",
        description = "Obtiene una lista completa de notificaciones con enlaces HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "No existen notificaciones registradas")
    })
    public CollectionModel<EntityModel<Notificacion>> listar() {

        List<EntityModel<Notificacion>> notificaciones =
                service.listar()
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        return CollectionModel.of(notificaciones);
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
    public EntityModel<Notificacion> buscarPorId(
            @Parameter(description = "ID de la notificación", required = true)
            @PathVariable Long id) {

        return assembler.toModel(
                service.buscarPorId(id));
    }

    @PostMapping
    @Operation(
        summary = "Crear notificación",
        description = "Registra una nueva notificación en el sistema"
    )
    public ResponseEntity<EntityModel<Notificacion>> guardar(
            @Valid @RequestBody Notificacion notificacion) {

        return ResponseEntity.ok(
                assembler.toModel(
                        service.guardar(notificacion)));
    }

    @PutMapping("/{id}/leer")
    @Operation(
        summary = "Marcar notificación como leída",
        description = "Actualiza el estado de una notificación a leída"
    )
    public ResponseEntity<EntityModel<Notificacion>> marcarComoLeida(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assembler.toModel(
                        service.marcarComoLeida(id)));
    }

    @GetMapping("/modulo/{modulo}")
    @Operation(
        summary = "Buscar notificaciones por módulo",
        description = "Obtiene todas las notificaciones pertenecientes a un módulo específico"
    )
    public CollectionModel<EntityModel<Notificacion>> buscarPorModulo(
            @PathVariable String modulo) {

        List<EntityModel<Notificacion>> notificaciones =
                service.buscarPorModulo(modulo)
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        return CollectionModel.of(notificaciones);
    }

    @GetMapping("/noleidas")
    @Operation(
        summary = "Obtener notificaciones no leídas",
        description = "Retorna todas las notificaciones pendientes de lectura"
    )
    public CollectionModel<EntityModel<Notificacion>> buscarNoLeidas() {

        List<EntityModel<Notificacion>> notificaciones =
                service.buscarNoLeidas()
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        return CollectionModel.of(notificaciones);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar notificación",
        description = "Elimina una notificación existente"
    )
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/citas")
    @Operation(
        summary = "Obtener notificaciones de citas",
        description = "Obtiene mensajes relacionados con citas médicas"
    )
    public ResponseEntity<List<String>> obtenerNotificacionesCitas() {

        return ResponseEntity.ok(
                service.obtenerNotificacionesCitas());
    }

    @GetMapping("/recetas")
    @Operation(
        summary = "Obtener notificaciones de recetas",
        description = "Obtiene mensajes relacionados con recetas médicas"
    )
    public ResponseEntity<List<String>> obtenerNotificacionesRecetas() {

        return ResponseEntity.ok(
                service.obtenerNotificacionesRecetas());
    }

    @GetMapping("/examenes")
    @Operation(
        summary = "Obtener notificaciones de exámenes",
        description = "Obtiene mensajes relacionados con exámenes médicos"
    )
    public ResponseEntity<List<String>> obtenerNotificacionesExamenes() {

        return ResponseEntity.ok(
                service.obtenerNotificacionesExamenes());
    }
}