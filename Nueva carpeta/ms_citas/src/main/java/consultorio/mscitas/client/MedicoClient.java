package consultorio.mscitas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "msmedico")
public interface MedicoClient {

    @GetMapping("/api/medicos/{id}")
    ResponseEntity<Object> obtenerMedico(@PathVariable Long id);
    
}