package consultorio.msmedico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import consultorio.msmedico.model.Medico;
import consultorio.msmedico.service.MedicoService;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    //registrar
    @PostMapping
    public ResponseEntity<?> registrarMed(@RequestBody Medico medico){
        if(medico == null){
            return ResponseEntity.badRequest().body("Datos del medico invalidos");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(medico));
    }

    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizaMed(
        @PathVariable Long id,
        @RequestBody Medico medico){
        if(medico==null){
            return ResponseEntity.badRequest().body("Datos invalidos");
        }

        return ResponseEntity.ok(service.actualizar(id, medico));
    }

    //listar todo
    @GetMapping
    public ResponseEntity<List<Medico>> listarTodoMed(){
        return ResponseEntity.ok(service.listarTodo());
    }
    
    //Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity <Medico> obtenerTodoIdMed(@PathVariable("id") Long id){
        return service.obtenerPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }// en el .map ~si esta el medico hace esto~ - ResponseEntity::ok es para hacer una referencia a un metodo. 
    //Es lo mismo que poner medico -> ResponseEntity.ok(medico)

    //eliminar(por logico)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id){
        if(!service.eliminarLogic(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Medico eliminado correctamente");
    }

    //---- busquedas especificas ----

    //buscar rut
    @GetMapping("/rut/{rut}/{dv}")
    public ResponseEntity<Medico> buscarPorRutMed(@PathVariable Integer rut, @PathVariable String dv){
        return service.buscarPorRut(rut, dv).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //buscar nombre
    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<List<Medico>> buscarPorNombreMed(@PathVariable String nombre){
        List<Medico> lista = service.buscarPorNombre(nombre.trim());
        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.buscarPorNombre(nombre.trim()));
    }
    //buscar especialidad
    @GetMapping("/buscar/esp/{especialidad}")
    public ResponseEntity<List<Medico>> buscarPorEspecialidadEsp(@PathVariable String especialidad){
        
        List<Medico> lista = service.buscarPorEspecialidad(especialidad);

        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(service.buscarPorEspecialidad(especialidad));
    }
}
