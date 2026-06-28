package com.consultorio.msAutentificacion.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // 1. LLAVE SECRETA: Se usa para firmar el token.
    // En producción, esto debe ir en el application.properties o variables de
    // entorno.
    private final String JWT_SECRET = "EstaEsUnaLlaveSuperSecretaYMuyLargaParaSeguridad123456";

    // El token durará 24 horas (en milisegundos)
    private final int JWT_EXPIRATION_MS = 86400000;

    /**
     * Genera la clave secreta que se utilizará para firmar
     * y validar los tokens JWT.
     *
     * Se transforma el String JWT_SECRET en una SecretKey
     * compatible con el algoritmo HMAC-SHA.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    // REQUERIMIENTO: "Generar token (JWT)"
    /**
     * Genera un token JWT para un usuario autenticado.
     *
     * El token contendrá:
     * - El nombre de usuario (subject).
     * - La fecha de emisión.
     * - La fecha de expiración.
     * - Una firma digital para evitar modificaciones.
     *
     * @param username Nombre de usuario autenticado.
     * @return Token JWT generado.
     */
    public String generarToken(String username) {
        return Jwts.builder()

                // Almacena el nombre de usuario dentro del token
                .subject(username)

                // Registra la fecha y hora en que se genera el token
                .issuedAt(new Date())

                // Define cuándo expirará el token
                // (24 horas después de su creación)
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))

                // Firma digitalmente el token utilizando la clave secreta
                // para garantizar su autenticidad e integridad
                .signWith(getSigningKey())

                // Construye y devuelve el JWT en formato String
                .compact();
    }

    // REQUERIMIENTO: "Validar token"
    /**
     * Valida que un token JWT sea correcto.
     *
     * Verifica:
     * - Que la firma sea válida.
     * - Que el token no haya sido modificado.
     * - Que el token no esté expirado.
     *
     * @param authToken Token JWT recibido desde la petición.
     * @return true si el token es válido, false si presenta algún problema.
     */
    public boolean validarToken(String authToken) {
        try {

            // Construye el parser JWT utilizando la clave secreta
            Jwts.parser()

                    // Define la clave que se utilizará para verificar la firma
                    .verifyWith(getSigningKey())

                    // Construye el parser
                    .build()

                    // Analiza y valida el token
                    .parseSignedClaims(authToken);

            // Si no se produce ninguna excepción, el token es válido
            return true;

        } catch (JwtException e) {

            // Si ocurre una excepción:
            // - Token expirado
            // - Firma incorrecta
            // - Token mal formado
            // - Token alterado
            return false;
        }
    }

    // Extraer el username para saber quién está haciendo la petición
    /**
     * Obtiene el nombre de usuario almacenado dentro del token JWT.
     *
     * El username fue guardado previamente mediante:
     * .setSubject(username)
     *
     * @param token Token JWT válido.
     * @return Nombre de usuario contenido en el token.
     */
    public String obtenerUsernameDeToken(String token) {

        return Jwts.parser()

                // Verifica la firma usando la clave secreta
                .verifyWith(getSigningKey())

                // Construye el parser
                .build()

                // Extrae los claims del token
                .parseSignedClaims(token)

                // Obtiene el contenido (payload)
                .getPayload()

                // Recupera el subject (username)
                .getSubject();
    }
}