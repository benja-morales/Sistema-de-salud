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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
public class ExamenController {

    private final ExamenService examenService;

    @PostMapping
    public Examen guardar(@Valid @RequestBody Examen examen) {
        return examenService.guardar(examen);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        examenService.eliminar(id);
    }

    @GetMapping
    public List<Examen> listar(){
        return examenService.listar();
    }

    //Buscar
    @GetMapping("/{id}")
    public Examen buscar(@PathVariable Long id){
        return examenService.buscar(id);
    }

    //Examen por paciente
    @GetMapping("/paciente/{idPaciente}")
    public List<Examen> buscarPorPaciente(@PathVariable long idPaciente){
        return examenService.buscarPorPaciente(idPaciente);
    }

    //Examen por fecha
    @GetMapping("/fecha")
    public List<Examen> buscarPorFecha(@RequestParam LocalDate fecha){
        return examenService.buscarPorFecha(fecha);    
    }

    //Cambio estado
    @PutMapping("/{id}/estado")
    public Examen cambiarEstado(@PathVariable Long id,@RequestBody Map<String, String> body) {
        return examenService.cambiarEstado(id, body.get("estado"));
    }

}
