package com.consultorio.msfarmacia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.consultorio.msfarmacia.model.Medicamento;
import com.consultorio.msfarmacia.repository.MedicamentoRepository;

@Service
public class MedicamentoService {

    private final MedicamentoRepository repository;

    public MedicamentoService(MedicamentoRepository repository) {
        this.repository = repository;
    }

    public List<Medicamento> listar() {
        return repository.findAll();
    }

    public Medicamento buscarId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
    }

    private void validarDatos(Medicamento medicamento) {
        if (medicamento.getPrecio() <= 0) { throw new RuntimeException("El precio debe ser mayor a 0");
        }
        if (medicamento.getStock() < 0) { throw new RuntimeException("El stock no puede ser negativo");
        }
        if (medicamento.getNombre().length() < 3) { throw new RuntimeException("El nombre debe tener al menos 3 caracteres");
        }
        if (medicamento.getDescripcion().length() < 5) { throw new RuntimeException("La descripcion es demasiado corta");
        }
    }

    public Medicamento guardar(Medicamento medicamento) {

        validarDatos(medicamento);

        if (repository.existsByNombre(medicamento.getNombre())) {
            throw new RuntimeException("El medicamento ya existe");
        }

        return repository.save(medicamento);
    }

    public Medicamento actualizar(Long id, Medicamento medicamento) {

        validarDatos(medicamento);

        Medicamento actual = buscarId(id);

        actual.setDescripcion(medicamento.getDescripcion());
        actual.setStock(medicamento.getStock());
        actual.setPrecio(medicamento.getPrecio());
        actual.setLaboratorio(medicamento.getLaboratorio());

    return repository.save(actual);
    }   

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Medicamento no encontrado");
        }

        repository.deleteById(id);
    }

    
}