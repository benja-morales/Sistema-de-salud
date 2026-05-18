package com.consultorio.msAutentificacion.security;


import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // 1. LLAVE SECRETA: Se usa para firmar el token. 
    // En producción, esto debe ir en el application.properties o variables de entorno.
    private final String JWT_SECRET = "EstaEsUnaLlaveSuperSecretaYMuyLargaParaSeguridad123456";
    
    // El token durará 24 horas (en milisegundos)
    private final int JWT_EXPIRATION_MS = 86400000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    // REQUERIMIENTO: "Generar token (JWT)"
    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // REQUERIMIENTO: "Validar token"
    public boolean validarToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {
            // El token puede estar expirado, mal formado o ser falso
            return false;
        }
    }

    // Extraer el username para saber quién está haciendo la petición
    public String obtenerUsernameDeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}