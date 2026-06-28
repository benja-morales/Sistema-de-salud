package consultorio.mscitas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import consultorio.mscitas.controller.CitasControllerV2;
import consultorio.mscitas.model.Citas;

@Component
public class CitasModelAssembler implements RepresentationModelAssembler<Citas, EntityModel<Citas>>{

    @Override
    public EntityModel<Citas> toModel(Citas cita) {

        return EntityModel.of(
                cita,

                linkTo(methodOn(CitasControllerV2.class)
                        .obtener(cita.getIdCita()))
                        .withSelfRel(),

                linkTo(methodOn(CitasControllerV2.class)
                        .listar())
                        .withRel("citas"));
    }
}
