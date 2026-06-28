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

@RestController
@RequestMapping("/api/citas")
public class CitasController {
    @Autowired
    private CitasService service;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Citas citas){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(citas));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/reagendar")
    public ResponseEntity<?> reagendar(@PathVariable Long id, @RequestBody Citas cita){
        try{
            return ResponseEntity.ok(service.reagendar(id, cita));
        } catch (RuntimeException e){
            
            if(e.getMessage().contains("no encontrada")){
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id){
        try{
            return ResponseEntity.ok(service.cancelar(id));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Citas cita){
        try{
            return ResponseEntity.ok(service.cambiarEstado(id, cita.getEstado()));
        } catch (RuntimeException e){
            if(e.getMessage().contains("no encontrada")){
                return ResponseEntity.notFound().build(); 
            }

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id){
        try{
            return ResponseEntity.ok(service.obtener(id));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> listar(){
        List<Citas> lista = service.listar();
        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<?> porPaciente(@PathVariable Long id){
        List<Citas> lista = service.porPaciente(id);
        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/medico/{id}")
    public ResponseEntity<?> porMedico(@PathVariable Long id){
        List<Citas> lista = service.porMedico(id);
        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<?> porFecha(@PathVariable 
        @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha){
        List<Citas> lista = service.porFecha(fecha);
        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
}
