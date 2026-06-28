package com.consultorio.msAutentificacion.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultorio.msAutentificacion.modelo.ERol;
import com.consultorio.msAutentificacion.modelo.Rol;
import com.consultorio.msAutentificacion.modelo.Usuario;
import com.consultorio.msAutentificacion.repository.RolRepository;
import com.consultorio.msAutentificacion.repository.UsuarioRepository;
import com.consultorio.msAutentificacion.security.JwtUtils;

@Service
public class AutentificacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder; // <-- Esto trae el BCrypt de tu SecurityConfig

    public void registrar(Usuario usuario) {
        // 1. Encriptamos la contraseña plana (ej: "123")
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        
        // 2. Guardamos la versión encriptada en el objeto
        usuario.setPassword(passwordEncriptada);

        // 3. Guardamos en la base de datos
        usuarioRepository.save(usuario);
    }

    // REQUERIMIENTO: "Registrar usuario"
    @Transactional
    public Usuario registrar(Usuario usuario, Set<String> rolesStr) {
        
        // 1. REQUERIMIENTO: "Usuario único"
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("Error: El username ya está registrado");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Error: El email ya está en uso");
        }

        // 2. REQUERIMIENTO: "Contraseña segura"
        // Encriptamos antes de guardar en MySQL
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // 3. REQUERIMIENTO: "Asignar roles (admin, médico, paciente)"
        Set<Rol> roles = new HashSet<>();
        if (rolesStr == null || rolesStr.isEmpty()) {
            Rol userRol = rolRepository.findByNombre(ERol.ROLE_PACIENTE)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRol);
        } else {
            rolesStr.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin" -> roles.add(rolRepository.findByNombre(ERol.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Rol ADMIN no existe")));
                    case "medico" -> roles.add(rolRepository.findByNombre(ERol.ROLE_MEDICO)
                            .orElseThrow(() -> new RuntimeException("Error: Rol MEDICO no existe")));
                    default -> roles.add(rolRepository.findByNombre(ERol.ROLE_PACIENTE)
                            .orElseThrow(() -> new RuntimeException("Error: Rol PACIENTE no existe")));
                }
            });
        }

        usuario.setRoles(roles);
        return usuarioRepository.save(usuario);
    }

    // REQUERIMIENTO: "Login" y "Generar token (JWT)"
    public String login(String username, String password) {
        // Spring Security valida las credenciales automáticamente
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Si la autenticación es correcta, generamos el token
        return jwtUtils.generarToken(username);
    }

    //eliminar usuario
    @Transactional
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Error: El usuario con ID " + id + " no existe.");
        }
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> listarTodos() {
    return usuarioRepository.findAll();
}
}