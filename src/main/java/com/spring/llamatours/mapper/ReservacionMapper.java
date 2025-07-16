package com.spring.llamatours.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.spring.llamatours.DTOs.DestinoDTO;
import com.spring.llamatours.DTOs.ReservacionDTO;
import com.spring.llamatours.enums.EstadoReserva;
import com.spring.llamatours.model.Destino;
import com.spring.llamatours.model.Reservacion;

@Component
public class ReservacionMapper {

    public ReservacionDTO toDTO(Reservacion entity) {
        if (entity == null) {
            return null;
        }

        ReservacionDTO dto = new ReservacionDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setDestinoId(entity.getDestino().getId());
        dto.setFechaReserva(entity.getFechaReserva());
        dto.setCantidadPersonas(entity.getCantidadPersonas());
        dto.setNombresPasajeros(entity.getNombresPasajeros());
        dto.setEstado(entity.getEstado() != null ? entity.getEstado().name() : null);

        // ✅ Agregar esto
        Destino destino = entity.getDestino();
        if (destino != null) {
            DestinoDTO destinoDTO = new DestinoDTO();
            destinoDTO.setId(destino.getId());
            destinoDTO.setTitulo(destino.getTitulo());
            destinoDTO.setDescripcion(destino.getDescripcion());
            destinoDTO.setActividades(destino.getActividades());
            destinoDTO.setPrecio(destino.getPrecio());
            destinoDTO.setFechaInicio(destino.getFechaInicio());
            destinoDTO.setFechaFin(destino.getFechaFin());
            destinoDTO.setDias(destino.getDias());
            destinoDTO.setCuposDisponibles(destino.getCuposDisponibles());
            destinoDTO.setDepartamento(destino.getDepartamento().name()); // si es enum
            destinoDTO.setProvincia(destino.getProvincia());
            destinoDTO.setUrlimg1(destino.getUrlimg1());
            destinoDTO.setUrlimg2(destino.getUrlimg2());
            destinoDTO.setUrlimg3(destino.getUrlimg3());
            destinoDTO.setUrlimg4(destino.getUrlimg4());
            dto.setDestino(destinoDTO);
        }

        return dto;
    }

    public Reservacion toEntity(ReservacionDTO dto) {
        if (dto == null) {
            return null;
        }

        Reservacion entity = new Reservacion();
        entity.setId(dto.getId());
        // dto.setUsuarioId(dto.getUsuario().getId());
        // dto.setDestinoId(dto.getDestino().getId());
        entity.setFechaReserva(dto.getFechaReserva());
        entity.setCantidadPersonas(dto.getCantidadPersonas());
        entity.setNombresPasajeros(dto.getNombresPasajeros());
        entity.setEstado(dto.getEstado() != null ? EstadoReserva.valueOf(dto.getEstado()) : null);
        return entity;
    }
    public List<ReservacionDTO> toDTOList(List<Reservacion> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
