package com.consultorio.msnotificaciones.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.consultorio.msnotificaciones.controller.NotificacionControllerV2;
import com.consultorio.msnotificaciones.model.Notificacion;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>> {

    @Override
    public EntityModel<Notificacion> toModel(Notificacion notificacion) {

        return EntityModel.of(
                notificacion,

                linkTo(methodOn(NotificacionControllerV2.class)
                        .buscarPorId(notificacion.getId()))
                        .withSelfRel(),

                linkTo(methodOn(NotificacionControllerV2.class)
                        .listar())
                        .withRel("notificaciones")
        );
    }
}