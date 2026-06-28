package com.consultorio.msAutentificacion.controller;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.msAutentificacion.modelo.Usuario;
import com.consultorio.msAutentificacion.service.AutentificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autentificacion", description = "Operaciones relacionadas con la gestión de autentificacion")
@CrossOrigin(origins = "*") // Para permitir peticiones desde el Frontend
public class AutentificacionController {

    @Autowired
    private AutentificacionService authService;

    // REGISTRAR USUARIO
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroDto registroDto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(registroDto.getUsername());
        usuario.setEmail(registroDto.getEmail());
        usuario.setPassword(registroDto.getPassword());

        authService.registrar(usuario, registroDto.getRoles());
        return ResponseEntity.ok("Usuario registrado exitosamente con sus roles");
    }

    // REQUERIMIENTO: "Login" y "Generar token (JWT)"
    @Operation(
        summary = "Registrar un nuevo usuario", 
        description = "Crea una cuenta de usuario en el sistema asignándole los roles correspondientes."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Usuario registrado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de registro inválidos (Faltan campos obligatorios o formato incorrecto)",
            content = @Content
        )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto.getUsername(), loginDto.getPassword());
        
        // Devolvemos el token envuelto en un objeto para que sea un JSON válido
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // Dentro de AuthController.java

    @Operation(
        summary = "Eliminar un usuario por ID", 
        description = "Elimina permanentemente una cuenta de usuario de la base de datos a través de su identificador numérico."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Usuario eliminado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "El usuario con el ID especificado no existe",
            content = @Content
        )
    })
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')") //esto si ya se configuro securityConfig con roles
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        authService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    //LISTAR USURIOS REGISTRADOS
    @Operation(
        summary = "Listar todos los usuarios", 
        description = "Devuelve una lista con todos los usuarios registrados en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de usuarios obtenida con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))
        )
    })
    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios() {
    // Usamos el service en lugar del repository directamente
            return ResponseEntity.ok(authService.listarTodos());
    }

    

    // --- DTO la comunicación ---
    
@Data
    public static class RegistroDto {
        @Schema(description = "Nombre de usuario único", example = "juan.perez")
        private String username;
        
        @Schema(description = "Correo electrónico del usuario", example = "juan@consultorio.com")
        private String email;
        
        @Schema(description = "Contraseña de la cuenta", example = "SecurePassword123")
        private String password;
        
        @Schema(description = "Lista de roles asignados al usuario", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
        private Set<String> roles;
    }

    @Data
    public static class LoginDto {
        @Schema(description = "Nombre de usuario registrado", example = "juan.perez", requiredMode = Schema.RequiredMode.REQUIRED)
        private String username;
        
        @Schema(description = "Contraseña del usuario", example = "SecurePassword123", requiredMode = Schema.RequiredMode.REQUIRED)
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class JwtResponse {
        @Schema(description = "Token de acceso generado por el servidor en formato JWT")
        private String token;
        
        @Schema(description = "Tipo de token para la cabecera Authorization", example = "Bearer")
        private String tipo = "Bearer";

        public JwtResponse(String token) {
            this.token = token;
        }
    }
}
