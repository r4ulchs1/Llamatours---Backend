package com.spring.llamatours.mapper;

import org.springframework.stereotype.Component;

import com.spring.llamatours.DTOs.ReservacionDTO;
import com.spring.llamatours.enums.EstadoReserva;
import com.spring.llamatours.model.Reservacion;

@Component
public class ReservacionMapper {
    
    public ReservacionDTO toDTO(Reservacion entity){
        if (entity==null) {
            return null;
        }

        ReservacionDTO dto= new ReservacionDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setDestinoId(entity.getDestino().getId());
        dto.setFechaReserva(entity.getFechaReserva());
        dto.setCantidadPersonas(entity.getCantidadPersonas());
        dto.setNombresPasajeros(entity.getNombresPasajeros());
        dto.setEstado(entity.getEstado()!=null?entity.getEstado().name():null);
        return dto;
    }

    public Reservacion toEntity(ReservacionDTO dto){
        if (dto==null) {
            return null;
        }

        Reservacion entity= new Reservacion();
        entity.setId(dto.getId());
        // dto.setUsuarioId(dto.getUsuario().getId());
        // dto.setDestinoId(dto.getDestino().getId());
        entity.setFechaReserva(dto.getFechaReserva());
        entity.setCantidadPersonas(dto.getCantidadPersonas());
        entity.setNombresPasajeros(dto.getNombresPasajeros());
        entity.setEstado(dto.getEstado()!=null?EstadoReserva.valueOf(dto.getEstado()):null);
        return entity;
    }
}
