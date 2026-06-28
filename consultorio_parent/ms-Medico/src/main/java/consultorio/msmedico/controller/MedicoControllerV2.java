package consultorio.msmedico.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import consultorio.msmedico.assemblers.MedicoModelAssembler;
import consultorio.msmedico.model.Medico;
import consultorio.msmedico.service.MedicoService;

@RestController
@RequestMapping("/api/v2/medicos")
public class MedicoControllerV2 {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Medico>> getAllMedicos() {

        List<EntityModel<Medico>> medicos = medicoService.listarTodo().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(medicos,
                linkTo(methodOn(MedicoControllerV2.class).getAllMedicos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Medico> getMedicoById(@PathVariable Long id) {
        Medico medico = medicoService.obtenerPorId(id).orElseThrow(() -> new RuntimeException("Médico no encontreado"));

        return assembler.toModel(medico);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Medico>> createMedico(@RequestBody Medico medico) {
        Medico nuevo = medicoService.registrar(medico);

        return ResponseEntity.created(linkTo(methodOn(MedicoControllerV2.class)
                .getMedicoById(nuevo.getIdMed())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Medico>> updateMedico(
            @PathVariable Long id,
            @RequestBody Medico medico) {

        Medico actualizado = medicoService.actualizar(id, medico);

        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteMedico(@PathVariable Long id) {

        medicoService.eliminarLogic(id);

        return ResponseEntity.noContent().build();
    }

}
