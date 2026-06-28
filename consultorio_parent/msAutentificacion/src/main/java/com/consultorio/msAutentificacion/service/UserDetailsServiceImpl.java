package com.consultorio.msAutentificacion.service;

//public class UserDetailsService {

//}



import com.consultorio.msAutentificacion.modelo.Usuario;
import com.consultorio.msAutentificacion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos el usuario en la BD por su nombre de usuario
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));

        // Retornamos un objeto User de Spring Security con los datos de nuestro usuario
        // Por ahora lo enviamos con una lista de roles vacía para probar, 
        // luego le pasaremos sus roles reales.
        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(), 
                usuario.getPassword(), 
                new ArrayList<>() 
        );
    }
}