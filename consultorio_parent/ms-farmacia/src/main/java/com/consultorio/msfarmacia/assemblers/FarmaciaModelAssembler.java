package com.consultorio.msfarmacia.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

import com.consultorio.msfarmacia.controller.MedicamentoControllerV2;
import com.consultorio.msfarmacia.model.Medicamento;

@Component
public class FarmaciaModelAssembler implements RepresentationModelAssembler<Medicamento, EntityModel<Medicamento>> {

    @Override
    public EntityModel<Medicamento> toModel(Medicamento medicamento) {

        return EntityModel.of(
                medicamento,
                linkTo(MedicamentoControllerV2.class)
                        .slash(medicamento.getId())
                        .withSelfRel(),
                linkTo(MedicamentoControllerV2.class)
                        .withRel("medicamentos")
        );
    }
}