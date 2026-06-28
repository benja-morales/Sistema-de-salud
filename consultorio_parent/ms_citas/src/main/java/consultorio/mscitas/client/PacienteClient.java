package consultorio.mscitas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "mspaciente")
public interface PacienteClient {
    
    @GetMapping("/api/pacientes/{id}")
    ResponseEntity<Object> obtenerPaciente(@PathVariable Long id);

}

    