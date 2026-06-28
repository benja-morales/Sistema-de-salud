package com.consultorio.msAutentificacion.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.consultorio.msAutentificacion.modelo.ERol;
import com.consultorio.msAutentificacion.modelo.Rol;
import com.consultorio.msAutentificacion.modelo.Usuario;
import com.consultorio.msAutentificacion.repository.RolRepository;
import com.consultorio.msAutentificacion.repository.UsuarioRepository;
import com.consultorio.msAutentificacion.security.JwtUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;


@ExtendWith(MockitoExtension.class)
public class AutentificacionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AutentificacionService autentificacionService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {

        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setUsername("admin");
        usuario.setEmail("admin@email.com");
        usuario.setPassword("1234");
    }

    @Test
    void debeRegistrarUsuarioCorrectamente() {

        // Arrange:
        when(usuarioRepository.existsByUsername("admin"))
                .thenReturn(false);

        when(usuarioRepository.existsByEmail("admin@email.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("1234"))
                .thenReturn("passwordEncriptada");

        Rol rolAdmin = new Rol();
        rolAdmin.setNombre(ERol.ROLE_ADMIN);

        when(rolRepository.findByNombre(ERol.ROLE_ADMIN))
                .thenReturn(Optional.of(rolAdmin));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuario);

        Set<String> roles = new HashSet<>();
        roles.add("admin");

        // Act:
        Usuario resultado =
                autentificacionService.registrar(usuario, roles);

        // Assert:
        assertNotNull(resultado);

        verify(passwordEncoder).encode("1234");

        verify(usuarioRepository)
                .save(any(Usuario.class));
    }

    @Test
    void debeLanzarExcepcionSiUsernameExiste() {

        // Arrange:
        when(usuarioRepository.existsByUsername("admin"))
                .thenReturn(true);

        Set<String> roles = new HashSet<>();

        // Act + Assert:
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> {

            autentificacionService.registrar(usuario, roles);

        });

        assertEquals(
                "Error: El username ya está registrado",
                exception.getMessage());
    }

    @Test
    void debeEncriptarPassword() {

        // Arrange:
        when(passwordEncoder.encode(anyString()))
                .thenReturn("passwordSegura");

        // Act:
        autentificacionService.registrar(usuario);

        // Assert:
        verify(passwordEncoder).encode("1234");

        verify(usuarioRepository)
                .save(usuario);
    }
}