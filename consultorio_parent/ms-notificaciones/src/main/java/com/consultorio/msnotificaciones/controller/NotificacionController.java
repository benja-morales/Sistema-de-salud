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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    // =========================
    // CRUD NORMAL
    // =========================

    // LISTAR
    @GetMapping
    public ResponseEntity<List<Notificacion>>
    listar() {

        return ResponseEntity.ok(
                service.listar());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion>
    buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id));
    }

    // CREAR
    @PostMapping
    public ResponseEntity<Notificacion>
    guardar(
            @Valid
            @RequestBody
            Notificacion notificacion) {

        return ResponseEntity.ok(
                service.guardar(notificacion));
    }

    // MARCAR COMO LEIDA
    @PutMapping("/{id}/leer")
    public ResponseEntity<Notificacion>
    marcarComoLeida(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.marcarComoLeida(id));
    }

    // BUSCAR POR MODULO
    @GetMapping("/modulo/{modulo}")
    public ResponseEntity<List<Notificacion>>
    buscarPorModulo(
            @PathVariable String modulo) {

        return ResponseEntity.ok(
                service.buscarPorModulo(modulo));
    }

    // BUSCAR NO LEIDAS
    @GetMapping("/noleidas")
    public ResponseEntity<List<Notificacion>>
    buscarNoLeidas() {

        return ResponseEntity.ok(
                service.buscarNoLeidas());
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // NOTIFICACIONES CITAS
    // =========================

    @GetMapping("/citas")
    public ResponseEntity<List<String>>
    obtenerNotificacionesCitas() {

        return ResponseEntity.ok(
                service.obtenerNotificacionesCitas());
    }

    // =========================
    // NOTIFICACIONES RECETAS
    // =========================

    @GetMapping("/recetas")
    public ResponseEntity<List<String>>
    obtenerNotificacionesRecetas() {

        return ResponseEntity.ok(
                service.obtenerNotificacionesRecetas());
    }

    // =========================
    // NOTIFICACIONES EXAMENES
    // =========================

    @GetMapping("/examenes")
    public ResponseEntity<List<String>>
    obtenerNotificacionesExamenes() {

        return ResponseEntity.ok(
                service.obtenerNotificacionesExamenes());
    }
}