package com.consultorio.msfarmacia.controller;

import java.util.List;

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

import com.consultorio.msfarmacia.model.Medicamento;
import com.consultorio.msfarmacia.service.MedicamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoService service;

    public MedicamentoController(MedicamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarId(id));
    }

    @PostMapping
    public ResponseEntity<Medicamento> guardar(
            @Valid @RequestBody Medicamento medicamento) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(medicamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> actualizar(
            @PathVariable Long id,
            @RequestBody Medicamento medicamento) {

        return ResponseEntity.ok(service.actualizar(id, medicamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok("Medicamento eliminado");
    }
}