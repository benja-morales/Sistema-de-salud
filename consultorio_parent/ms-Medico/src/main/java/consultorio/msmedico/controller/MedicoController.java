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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Medicos", description = "Operacionbes relacionadas con medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    // registrar
    @PostMapping
    @Operation(summary = "Registrar medicos", description = "Agrega o registra un medico al consultorio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medico registrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medico.class), examples = {
                    @ExampleObject(name = "MedicoRegistrado", value = """
                            {
                                "idMed": 1,
                                "pnomMed": "Juan",
                                "especialidad": "Cardiologia"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> registrarMed(@RequestBody Medico medico) {
        if (medico == null) {
            return ResponseEntity.badRequest().body("Datos del medico invalidos");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(medico));
    }

    // actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Medico", description = "Actualiza los datos de un medico en especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> actualizaMed(
            @Parameter(description = "ID del médico a actualizar", example = "1", required = true) @PathVariable Long id,
            @RequestBody Medico medico) {
        if (medico == null) {
            return ResponseEntity.badRequest().body("Datos invalidos");
        }

        return ResponseEntity.ok(service.actualizar(id, medico));
    }

    // listar todo
    @GetMapping
    @Operation(summary = "Obtener todos Los Medicos", description = "Obtiene una lista de todos los medicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Medico.class)), examples = {
                    @ExampleObject(name = "EjemploMedico", value = """
                            {
                                "idMed": 1,
                                "pnomMed": "Juan",
                                "snomMed": "Carlos",
                                "apePatMed": "Perez",
                                "apeMatMed": "Gonzalez",
                                "rutMed": 12345678,
                                "dvMed": "K",
                                "especialidad": "Cardiologia",
                                "email": "juan@consultorio.cl",
                                "telefono": "+56912345678",
                                "fechaContratacion": "2026-01-15",
                                "estado": true
                            }
                            """)
            }))
    })
    public ResponseEntity<List<Medico>> listarTodoMed() {
        return ResponseEntity.ok(service.listarTodo());
    }

    // Obtener por id
    @GetMapping("/{id}")
    @Operation(summary = "Obtener por la ID medico", description = "Obtiene los datos de un medico especifico, buscado por la ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medico encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    })
    public ResponseEntity<Medico> obtenerTodoIdMed(
            @Parameter(description = "ID del médico", example = "1", required = true) @PathVariable("id") Long id) {
        return service.obtenerPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }// en el .map ~si esta el medico hace esto~ - ResponseEntity::ok es para hacer
     // una referencia a un metodo.
     // Es lo mismo que poner medico -> ResponseEntity.ok(medico)

    // eliminar(por logico)
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Medico", description = "Elimina a un medico en especifico, utilizando la ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medico eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    })
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID del médico", example = "1", required = true) @PathVariable("id") Long id) {
        if (!service.eliminarLogic(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Medico eliminado correctamente");
    }

    // ---- busquedas especificas ----

    // buscar rut
    @GetMapping("/rut/{rut}/{dv}")
    @Operation(summary = "Buscar rut medico", description = "Busca a un medico en especifico, utilizando el rut")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    })
    public ResponseEntity<Medico> buscarPorRutMed(
            @Parameter(description = "RUN del médico", example = "11222333", required = true) @PathVariable Integer rut,
            @Parameter(description = "Dígito verificador", example = "k", required = true) @PathVariable String dv) {
        return service.buscarPorRut(rut, dv).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // buscar nombre
    @GetMapping("/buscar/nombre/{nombre}")
    @Operation(summary = "Buscar por nombre medico", description = "Busca al medico por su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médicos encontrados", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Medico.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron médicos")
    })
    public ResponseEntity<List<Medico>> buscarPorNombreMed(
            @Parameter(description = "Nombre del médico", example = "Juan", required = true) @PathVariable String nombre) {
        List<Medico> lista = service.buscarPorNombre(nombre.trim());
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.buscarPorNombre(nombre.trim()));
    }

    // buscar especialidad
    @GetMapping("/buscar/esp/{especialidad}")
    @Operation(summary = "Buscar por especialidad medico", description = "Busca el medico por su especialidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médicos encontrados", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Medico.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron médicos")
    })
    public ResponseEntity<List<Medico>> buscarPorEspecialidadEsp(
            @Parameter(description = "Especialidad médico", example = "Cardiología", required = true) @PathVariable String especialidad) {

        List<Medico> lista = service.buscarPorEspecialidad(especialidad);

        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(service.buscarPorEspecialidad(especialidad));
    }
}
