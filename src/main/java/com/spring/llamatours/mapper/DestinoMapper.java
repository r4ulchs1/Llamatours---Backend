package com.spring.llamatours.mapper;

import org.springframework.stereotype.Component;

import com.spring.llamatours.DTOs.DestinoDTO;
import com.spring.llamatours.enums.DepartamentoNombre;
import com.spring.llamatours.model.Destino;

@Component
public class DestinoMapper {

    public DestinoDTO toDTO(Destino entity){
        if (entity==null) {
            return null;
        }

        DestinoDTO dto= new DestinoDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setActividades(entity.getActividades());
        dto.setPrecio(entity.getPrecio());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setDias(entity.getDias());
        dto.setCuposDisponibles(entity.getCuposDisponibles());
        dto.setDepartamento(entity.getDepartamento()!=null?entity.getDepartamento().name():null);
        dto.setProvincia(entity.getProvincia());
        dto.setUrlimg1(entity.getUrlimg1());
        dto.setUrlimg2(entity.getUrlimg2());
        dto.setUrlimg3(entity.getUrlimg3());
        dto.setUrlimg4(entity.getUrlimg4());

        return dto;
    }

    public Destino toEntity(DestinoDTO dto){
        if (dto==null) {
            return null;
        }

        Destino entity= new Destino();
        entity.setId(dto.getId());
        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setActividades(dto.getActividades());
        entity.setPrecio(dto.getPrecio());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setDias(dto.getDias());
        entity.setCuposDisponibles(dto.getCuposDisponibles());
        entity.setDepartamento(dto.getDepartamento()!=null?DepartamentoNombre.valueOf(dto.getDepartamento()):null);
        entity.setProvincia(dto.getProvincia());
        entity.setUrlimg1(dto.getUrlimg1());
        entity.setUrlimg2(dto.getUrlimg2());
        entity.setUrlimg3(dto.getUrlimg3());
        entity.setUrlimg4(dto.getUrlimg4());

        return entity;
    }
}
