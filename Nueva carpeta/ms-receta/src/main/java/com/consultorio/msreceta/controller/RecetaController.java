package com.consultorio.msreceta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.msreceta.model.MedicamentoReceta;
import com.consultorio.msreceta.model.Receta;
import com.consultorio.msreceta.service.RecetaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<Receta>> listar() {

        return ResponseEntity.ok(
                recetaService.listar());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Receta> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recetaService.buscarPorId(id));
    }

    // BUSCAR POR PACIENTE
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Receta>> buscarPorPaciente(
            @PathVariable Long idPaciente) {

        return ResponseEntity.ok(
                recetaService.buscarPorPaciente(idPaciente));
    }

    // BUSCAR POR ESPECIALISTA
    @GetMapping("/medicos/{idMedico}")
    public ResponseEntity<List<Receta>> buscarPorMedico(
            @PathVariable Long idMedico) {

        return ResponseEntity.ok(
                recetaService.buscarPorMedico(idMedico));
    }

    // CREAR RECETA
    @PostMapping
    public ResponseEntity<Receta> guardar(
            @Valid @RequestBody Receta receta) {

        return ResponseEntity.ok(
                recetaService.guardar(receta));
    }

    // AGREGAR MEDICAMENTO
    @PostMapping("/{id}/medicamentos")
    public ResponseEntity<Receta> agregarMedicamento(
            @PathVariable Long id,
            @Valid @RequestBody MedicamentoReceta medicamento) {

        return ResponseEntity.ok(
                recetaService.agregarMedicamento(
                        id,
                        medicamento));
    }

    // ELIMINAR MEDICAMENTO
    @DeleteMapping("/{id}/medicamentos/{medicamentoId}")
    public ResponseEntity<Receta> eliminarMedicamento(
            @PathVariable Long id,
            @PathVariable Long medicamentoId) {

        return ResponseEntity.ok(
                recetaService.eliminarMedicamento(
                        id,
                        medicamentoId));
    }

    // ELIMINAR RECETA
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        recetaService.eliminar(id);

        return ResponseEntity.ok(
                "Receta eliminada correctamente");
    }
}