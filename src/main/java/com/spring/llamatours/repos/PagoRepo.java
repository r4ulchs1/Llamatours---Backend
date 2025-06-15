package com.spring.llamatours.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.llamatours.model.Pago;

public interface PagoRepo extends JpaRepository<Pago,Long> { 
    Optional<Pago> findByReservacionId(Long reservacionId);
}
