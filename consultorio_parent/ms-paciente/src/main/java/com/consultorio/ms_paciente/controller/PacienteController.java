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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    @GetMapping
    public List<Paciente> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Paciente> buscarPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(service.findByRut(rut));
    }

    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody @Valid Paciente paciente) {
        return new ResponseEntity<>(service.save(paciente), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable @NonNull Long id, @RequestBody Paciente paciente) {
        // Usamos 'service.update' que es el nombre que pusiste en tu lógica
        return ResponseEntity.ok(service.update(id, paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Valid @NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
