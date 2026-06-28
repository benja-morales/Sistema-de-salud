package consultorio.mscitas.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import consultorio.mscitas.assemblers.CitasModelAssembler;
import consultorio.mscitas.model.Citas;
import consultorio.mscitas.service.CitasService;

@RestController
@RequestMapping("/api/v2/citas")
public class CitasControllerV2 {

    @Autowired
    private CitasService service;

    @Autowired
    private CitasModelAssembler assembler;

    // Crear cita
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Citas>> crear(@RequestBody Citas citas) {

        Citas nuevaCita = service.crear(citas);

        return ResponseEntity
                .created(
                        linkTo(methodOn(CitasControllerV2.class)
                                .obtener(nuevaCita.getIdCita()))
                                .toUri())
                .body(assembler.toModel(nuevaCita));
    }

    // Reagendar cita
    @PutMapping(value = "/{id}/reagendar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Citas>> reagendar(
            @PathVariable Long id,
            @RequestBody Citas cita) {

        Citas citaActualizada = service.reagendar(id, cita);

        return ResponseEntity.ok(
                assembler.toModel(citaActualizada));
    }

    // Cancelar cita
    @PutMapping(value = "/{id}/cancelar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Citas>> cancelar(
            @PathVariable Long id) {

        Citas citaCancelada = service.cancelar(id);

        return ResponseEntity.ok(
                assembler.toModel(citaCancelada));
    }

    // Cambiar estado
    @PutMapping(value = "/{id}/estado", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Citas>> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Citas cita) {

        Citas citaActualizada = service.cambiarEstado(id, cita.getEstado());

        return ResponseEntity.ok(
                assembler.toModel(citaActualizada));
    }

    // Obtener por ID
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Citas> obtener(@PathVariable Long id) {

        Citas cita = service.obtener(id);

        return assembler.toModel(cita);
    }

    // Listar todas
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Citas>> listar() {

        List<EntityModel<Citas>> citas = service.listar()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                citas,
                linkTo(methodOn(CitasControllerV2.class)
                        .listar())
                        .withSelfRel());
    }

    // Buscar por paciente
    @GetMapping(value = "/paciente/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Citas>> porPaciente(
            @PathVariable Long id) {

        List<EntityModel<Citas>> citas = service.porPaciente(id)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                citas,
                linkTo(methodOn(CitasControllerV2.class)
                        .porPaciente(id))
                        .withSelfRel());
    }

    // Buscar por médico
    @GetMapping(value = "/medico/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Citas>> porMedico(
            @PathVariable Long id) {

        List<EntityModel<Citas>> citas = service.porMedico(id)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                citas,
                linkTo(methodOn(CitasControllerV2.class)
                        .porMedico(id))
                        .withSelfRel());
    }

    // Buscar por fecha
    @GetMapping(value = "/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Citas>> porFecha(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha) {

        List<EntityModel<Citas>> citas = service.porFecha(fecha)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                citas,
                linkTo(methodOn(CitasControllerV2.class)
                        .porFecha(fecha))
                        .withSelfRel());
    }

    // nuevos endpoints----

    // ---Busquedas
    // por estado
    @GetMapping(value = "/estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Citas>> porEstado(
            @PathVariable String estado) {

        List<EntityModel<Citas>> citas = service.porEstado(estado)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                citas,
                linkTo(methodOn(CitasControllerV2.class)
                        .porEstado(estado))
                        .withSelfRel());
    }

    // por citas de paciente por fecha
    @GetMapping(value = "/paciente/{id}/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Citas>> pacientePorFecha(

            @PathVariable Long id,

            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha) {

        List<EntityModel<Citas>> citas = service.pacientePorFecha(id, fecha)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                citas,
                linkTo(methodOn(CitasControllerV2.class)
                        .pacientePorFecha(id, fecha))
                        .withSelfRel());
    }

    // por citas de médico por fecha
    @GetMapping(value = "/medico/{id}/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Citas>> medicoPorFecha(

            @PathVariable Long id,

            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha) {

        List<EntityModel<Citas>> citas = service.medicoPorFecha(id, fecha)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                citas,
                linkTo(methodOn(CitasControllerV2.class)
                        .medicoPorFecha(id, fecha))
                        .withSelfRel());
    }

    // total de citas por medico
    @GetMapping(value = "/medico/{id}/total", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Long> totalPorMedico(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.totalPorMedico(id));
    }

    // entre dos fechas
    @GetMapping(value = "/rango/{inicio}/{fin}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Citas>> porRangoFechas(

            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio,

            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fin) {

        List<EntityModel<Citas>> citas = service.porRangoFechas(inicio, fin)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                citas,
                linkTo(methodOn(CitasControllerV2.class)
                        .porRangoFechas(inicio, fin))
                        .withSelfRel());
    }
}
