package com.consultorio.msreceta.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.consultorio.msreceta.controller.RecetaControllerV2;
import com.consultorio.msreceta.model.Receta;

@Component
public class RecetaModelAssembler implements RepresentationModelAssembler<Receta, EntityModel<Receta>> {

    @Override
    public EntityModel<Receta> toModel(Receta receta) {

        return EntityModel.of(
                receta,

                linkTo(methodOn(RecetaControllerV2.class)
                        .buscarPorId(receta.getId()))
                        .withSelfRel(),

                linkTo(methodOn(RecetaControllerV2.class)
                        .listar())
                        .withRel("recetas")
        );
    }
}