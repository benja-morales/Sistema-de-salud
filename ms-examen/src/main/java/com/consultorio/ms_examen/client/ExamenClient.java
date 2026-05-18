package com.consultorio.ms_examen.client;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// El nombre debe coincidir exactamente con el valor de 'spring.application.name' 
// definido en el application.properties del microservicio de Exámenes.
@FeignClient(name = "ms-examenes") 
public interface ExamenClient {

    // Obtener un examen específico por su ID
    @GetMapping("/api/examenes/{id}")
    Object obtenerExamenPorId(@PathVariable("id") Long id);

    // Listar todos los exámenes asociados a una atención médica específica
    @GetMapping("/api/examenes/atencion/{idAtencion}")
    List<Object> listarPorAtencion(@PathVariable("idAtencion") Long idAtencion);

    // Crear una nueva orden de examen desde otro microservicio
    @PostMapping("/api/examenes")
    Object crearOrdenExamen(@RequestBody Object examen);
}