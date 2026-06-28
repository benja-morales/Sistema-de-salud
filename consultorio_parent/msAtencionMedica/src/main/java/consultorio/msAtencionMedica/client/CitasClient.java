package consultorio.msAtencionMedica.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import consultorio.msAtencionMedica.data.Cita;

@FeignClient(name = "mscitas")
public interface CitasClient {

    @GetMapping("/api/citas/{id}")
    Cita obtenerCita(@PathVariable Long id);

}