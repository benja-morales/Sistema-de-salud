package com.consultorio.msAutentificacion.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc(addFilters = false)
public class JwtUtilsTest {

    private JwtUtils jwtUtils = new JwtUtils();

    @Test
    void debeGenerarToken() {

        // Arrange:
        String username = "admin";

        // Act:
        String token = jwtUtils.generarToken(username);

        // Assert:
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void debeValidarTokenCorrectamente() {

        // Arrange:
        String token = jwtUtils.generarToken("admin");

        // Act:
        boolean valido = jwtUtils.validarToken(token);

        // Assert:
        assertTrue(valido);
    }

    @Test
    void debeExtraerUsernameDelToken() {

        // Arrange:
        String token = jwtUtils.generarToken("admin");

        // Act:
        String username =
                jwtUtils.obtenerUsernameDeToken(token);

        // Assert:
        assertEquals("admin", username);
    }
}