package com.consultorio.ms_examen.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultorio.ms_examen.modelo.Examen;
import com.consultorio.ms_examen.repository.ExamenRepository;

import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor // Genera el constructor para la inyección de dependencias
public class ExamenService {

    private final ExamenRepository examenRepository;

    //listar examenes
    public List<Examen> listar(){
        return examenRepository.findAll();
    }

    //buscar examenes
    public Examen buscar(Long id) {
        return examenRepository.findById(id).
            orElseThrow(() -> new RuntimeException("Examen no encontrado"));
    }

    //guardar examen
    public Examen guardar (Examen examen){
        examen.setFechaSolicitud(LocalDateTime.now());
        if(examen.getEstado() == null||
            examen.getEstado().isBlank()){
                examen.setEstado("PENDIENTE");
            }
            return examenRepository.save(examen);
    }

    //Actualizar 
    public Examen actualizar(Long id, Examen examen){
        Examen existe = buscar(id);
        existe.setTipoExamen(examen.getTipoExamen());
        existe.setResultadoTexto(examen.getResultadoTexto());
        existe.setArchivoUrl(examen.getArchivoUrl());
        existe.setEstado(examen.getEstado());
        existe.setFechaResultado(examen.getFechaResultado());

        return examenRepository.save(existe);
    }

    //Eliminar
    public void eliminar(Long id){
        examenRepository.deleteById(id);
    }

    //Examenes por paciente
    public List<Examen> buscarPorPaciente(Long idPaciente){
        return examenRepository.findByIdPaciente(idPaciente);
    }

    //Examenes por fehca
    public List<Examen> buscarPorFecha(LocalDate fecha){
        return examenRepository.findByFechaResultado(fecha);
    }

    //cambiar estado
    public Examen cambiarEstado(Long id, String estado){
        Examen examen = buscar(id);
        examen.setEstado(estado);
        return examenRepository.save(examen);
    }

    
}