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
        Pago pago=pagoMapper.toEntity(pagoDTO);

        Reservacion reservacion=reservacionRepo.findById(pagoDTO.getReservacionId())
                                .orElseThrow(()-> new RuntimeException("Reservacion no encontrada para el pago"));

        if (pagoRepo.findByReservacionId(reservacion.getId()).isPresent()) {
            throw new RuntimeException("La reservacion ya tiene un pago");
        }
        
        BigDecimal monto= BigDecimal.valueOf(reservacion.getCantidadPersonas()).multiply(reservacion.getDestino().getPrecio());
        pago.setReservacion(reservacion);

        pago.setFechaPago(LocalDate.now());
        // pago.setMetodoPago(MetodoPago.EFECTIVO);
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setMonto(monto);

        Pago savedPago= pagoRepo.save(pago);

        reservacion.setEstado(EstadoReserva.CONFIRMADA);
        reservacionRepo.save(reservacion);

        return pagoMapper.toDTO(savedPago);
    }

    @Transactional
    public PagoDTO updatePago(Long id, PagoDTO pagoDTO){
        Pago pagoExiste= pagoRepo.findById(id)
                        .orElseThrow(()-> new RuntimeException("Pago no encontrrado"));
        // pagoExiste.setMonto(pagoDTO.getMonto());
        pagoExiste.setMetodoPago(pagoDTO.getMetodoPago()!=null?MetodoPago.valueOf(pagoDTO.getMetodoPago()):null);
        pagoExiste.setEstado(pagoDTO.getEstado()!=null?EstadoPago.valueOf(pagoDTO.getEstado()):null);
        // pagoExiste.setFechaPago(pagoDTO.getFechaPago());

        Pago updatedPago= pagoRepo.save(pagoExiste);
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
}
