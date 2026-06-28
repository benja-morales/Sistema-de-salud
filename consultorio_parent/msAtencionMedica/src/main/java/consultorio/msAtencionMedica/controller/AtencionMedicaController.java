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

@RestController
@RequestMapping("/api/atenciones")
public class AtencionMedicaController {

    @Autowired
    private AtencionMedicaService service;

    // llamar a todas las atenciones
    @GetMapping
    public ResponseEntity<?> listar() {

        List<AtencionMedica> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    // registrar atencion
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody AtencionMedica atencion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(atencion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // asociar cita
    @PostMapping("/cita/{idCita}")
    public ResponseEntity<?> asociar(@PathVariable Long idCita,
            @RequestBody AtencionMedica atencion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(service.asociarACita(idCita, atencion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // obetner por id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // agregar diagnostico
    @PutMapping("/{id}/diagnostico")
    public ResponseEntity<?> diagnostico(@PathVariable Long id,
            @RequestBody AtencionMedica atencion) {
        try {
            return ResponseEntity.ok(service.agregarDiagnostico(id, atencion.getDiagnostico()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // agregar observaciones
    @PutMapping("/{id}/observaciones")
    public ResponseEntity<?> observaciones(@PathVariable Long id,
            @RequestBody AtencionMedica atencion) {

        try {
            return ResponseEntity.ok(service.agregarobservaciones(id, atencion.getObservaciones()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
