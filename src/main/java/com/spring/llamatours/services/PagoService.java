package com.spring.llamatours.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.llamatours.DTOs.PagoDTO;
import com.spring.llamatours.enums.EstadoPago;
import com.spring.llamatours.enums.EstadoReserva;
import com.spring.llamatours.enums.MetodoPago;
import com.spring.llamatours.mapper.PagoMapper;
import com.spring.llamatours.model.Pago;
import com.spring.llamatours.model.Reservacion;
import com.spring.llamatours.repos.PagoRepo;
import com.spring.llamatours.repos.ReservacionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PagoService {
    private final PagoRepo pagoRepo;
    private final PagoMapper pagoMapper;
    private final ReservacionRepo reservacionRepo;

    @Transactional
    public List<PagoDTO> findAllPagos(){
        return pagoRepo.findAll().stream().map(pagoMapper::toDTO).collect(Collectors.toList());
    }
    
    @Transactional
    public Optional<PagoDTO> findPagoById(Long id){
        return pagoRepo.findById(id).map(pagoMapper::toDTO);
    }

    @Transactional
    public PagoDTO savePago(PagoDTO pagoDTO){
        Pago pago = pagoMapper.toEntity(pagoDTO);

        Reservacion reservacion = reservacionRepo.findById(pagoDTO.getReservacionId())
                                .orElseThrow(() -> new RuntimeException("Reservación no encontrada para el pago"));

        if (pagoRepo.findByReservacionId(reservacion.getId()).isPresent()) {
            throw new RuntimeException("La reservación ya tiene un pago");
        }

        // ✅ Validar monto proveniente del formulario
        BigDecimal montoEsperado = BigDecimal.valueOf(reservacion.getCantidadPersonas())
                                .multiply(reservacion.getDestino().getPrecio());

        if (pagoDTO.getMonto() == null || pagoDTO.getMonto().compareTo(montoEsperado) != 0) {
            throw new RuntimeException("El monto no es válido o fue manipulado.");
        }

        pago.setReservacion(reservacion);
        pago.setFechaPago(LocalDate.now());
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setMonto(pagoDTO.getMonto()); // ← Lo usas del formulario validado

        Pago savedPago = pagoRepo.save(pago);

        reservacion.setEstado(EstadoReserva.CONFIRMADA);
        reservacionRepo.save(reservacion);

        return pagoMapper.toDTO(savedPago);
    }


    @Transactional
    public PagoDTO updatePago(Long id, PagoDTO pagoDTO) {
        Pago pagoExiste = pagoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (pagoDTO.getMetodoPago() != null && !pagoDTO.getMetodoPago().isBlank()) {
            pagoExiste.setMetodoPago(MetodoPago.valueOf(pagoDTO.getMetodoPago()));
        }
        pagoExiste.setEstado(EstadoPago.COMPLETADO);

        pagoExiste.setFechaPago(LocalDate.now());

        Pago updatedPago = pagoRepo.save(pagoExiste);

        Reservacion reservacion = pagoExiste.getReservacion();
        reservacion.setEstado(EstadoReserva.CONFIRMADA);
        reservacionRepo.save(reservacion);

        return pagoMapper.toDTO(updatedPago);
    }

    @Transactional
    public boolean cancelarPagoById(Long id){
        if (pagoRepo.existsById(id)) {
            Pago deletedpago= pagoRepo.findById(id).orElseThrow(()-> new RuntimeException("NO se encontro el pago a eliminar"));
            deletedpago.setEstado(EstadoPago.REEMBOLSADO);
            Reservacion reservacionAsoc= deletedpago.getReservacion();
            reservacionAsoc.setEstado(EstadoReserva.CANCELADA);
            reservacionRepo.save(reservacionAsoc);
            // pagoRepo.deleteById(id); no se borra solo cambia el estado
            return true;
        }
        return false;
    }
    /////////////////////////////////////////////
    
    @Transactional
    public Optional<PagoDTO> findPagoByReservacionId(Long reservacoinId){
        return pagoRepo.findByReservacionId(reservacoinId).map(pagoMapper::toDTO);
    }

    @Transactional
    public Pago crearPagoPendienteParaReserva(Reservacion reservacion) {
        Pago pago = new Pago();
        pago.setReservacion(reservacion);
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setMetodoPago(null);
        pago.setFechaPago(null);

        BigDecimal monto = reservacion.getDestino().getPrecio()
                .multiply(BigDecimal.valueOf(reservacion.getCantidadPersonas()));
        pago.setMonto(monto);

        return pagoRepo.save(pago);
    }
}
