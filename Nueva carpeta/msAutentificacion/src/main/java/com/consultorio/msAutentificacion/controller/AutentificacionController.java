package com.consultorio.msAutentificacion.controller;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Para permitir peticiones desde el Frontend
public class AutentificacionController {

    @Autowired
    private AutentificacionService authService;

    // REQUERIMIENTO: "Registrar usuario"
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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto.getUsername(), loginDto.getPassword());
        
        // Devolvemos el token envuelto en un objeto para que sea un JSON válido
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // Dentro de AuthController.java

    @DeleteMapping("/eliminar/{id}")
    // @PreAuthorize("hasRole('ADMIN')") // Descomenta esto si ya configuraste SecurityConfig con roles
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        authService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }


    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios() {
    // Usamos el service en lugar del repository directamente
            return ResponseEntity.ok(authService.listarTodos());
    }

    

    // --- DTOs necesarios para la comunicación ---
    
    @Data
    public static class RegistroDto {
        private String username;
        private String email;
        private String password;
        private Set<String> roles;
    }

    @Data
    public static class LoginDto {
        private String username;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class JwtResponse {
        private String token;
        private String tipo = "Bearer";

        public JwtResponse(String token) {
            this.token = token;
        }
    }
}
