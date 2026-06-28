package consultorio.msAtencionMedica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API-Consultorio - Atención Medica 2026")
                        .version("1.0")
                        .description("Documentación de la API para la Atención Medica de Consultorio"));
    }
}
