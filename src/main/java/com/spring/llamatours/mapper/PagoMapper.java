package com.spring.llamatours.mapper;

import org.springframework.stereotype.Component;

import com.spring.llamatours.DTOs.PagoDTO;
import com.spring.llamatours.enums.EstadoPago;
import com.spring.llamatours.enums.MetodoPago;
import com.spring.llamatours.model.Pago;

@Component
public class PagoMapper {
    
    public PagoDTO toDTO(Pago entity){
        if (entity==null) {
            return null;
        }

        PagoDTO dto= new PagoDTO();
        dto.setId(entity.getId());
        dto.setReservacionId(entity.getReservacion().getId());
        dto.setMonto(entity.getMonto());
        dto.setMetodoPago(entity.getMetodoPago()!=null?entity.getMetodoPago().name():null);
        dto.setEstado(entity.getEstado()!=null?entity.getEstado().name():null);
        dto.setFechaPago(entity.getFechaPago());
        return dto;
    }

    public Pago toEntity(PagoDTO dto){
        if (dto==null) {
            return null;
        }

        Pago entity= new Pago();
        entity.setId(dto.getId());
        // entity.setReservacionId(dto.getReservacion().getId()); esto en servicio
        entity.setMonto(dto.getMonto());
        entity.setMetodoPago(dto.getMetodoPago()!=null?MetodoPago.valueOf(dto.getMetodoPago()):null);
        entity.setEstado(dto.getEstado()!=null?EstadoPago.valueOf(dto.getEstado()):null);
        entity.setFechaPago(dto.getFechaPago());
        return entity;
    }
}
