package consultorio.msAtencionMedica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsAtencionMedicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAtencionMedicaApplication.class, args);
	}

}
