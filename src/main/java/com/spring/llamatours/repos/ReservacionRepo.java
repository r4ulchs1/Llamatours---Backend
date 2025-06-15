package com.spring.llamatours.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.llamatours.model.Reservacion;

public interface ReservacionRepo extends JpaRepository<Reservacion,Long> {
    List<Reservacion> findByUsuarioId(Long usuarioId);
    List<Reservacion> findByDestinoId(Long destinoId);
}
