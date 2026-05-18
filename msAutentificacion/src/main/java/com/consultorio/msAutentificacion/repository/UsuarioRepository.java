package com.consultorio.msAutentificacion.repository;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.msAutentificacion.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Necesario para el proceso de Login (Spring Security lo usará)
    Optional<Usuario> findByUsername(String username);
    
    // Requerimiento: "Usuario único"
    // Estos métodos devuelven un booleano para validar antes de registrar
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    

    //username
}