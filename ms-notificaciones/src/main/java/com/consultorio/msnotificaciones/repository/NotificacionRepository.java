package com.consultorio.msnotificaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.msnotificaciones.model.Notificacion;

@Repository
public interface NotificacionRepository
        extends JpaRepository<Notificacion, Long> {

    // BUSCAR POR MODULO
    List<Notificacion> findByModulo(
            String modulo);

    // BUSCAR LEIDAS / NO LEIDAS
    List<Notificacion> findByLeida(
            Boolean leida);
}