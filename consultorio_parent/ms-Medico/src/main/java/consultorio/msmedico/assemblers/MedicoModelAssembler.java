package consultorio.msmedico.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import consultorio.msmedico.controller.MedicoControllerV2;
import consultorio.msmedico.model.Medico;

@Component
public class MedicoModelAssembler implements RepresentationModelAssembler<Medico, EntityModel<Medico>> {

        @Override
        public EntityModel<Medico> toModel(Medico medico) {
                return EntityModel.of(medico,
                                linkTo(methodOn(MedicoControllerV2.class)
                                                .getMedicoById(medico.getIdMed())).withSelfRel(),
                                linkTo(methodOn(MedicoControllerV2.class)
                                                .getAllMedicos()).withRel("medicos"));
        }

}
