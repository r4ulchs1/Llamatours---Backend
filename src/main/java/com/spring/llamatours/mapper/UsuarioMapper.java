package com.spring.llamatours.mapper;

import org.springframework.stereotype.Component;

import com.spring.llamatours.DTOs.UsuarioDTO;
import com.spring.llamatours.enums.RolNombre;
import com.spring.llamatours.model.Usuario;

@Component
public class UsuarioMapper {
    
    public UsuarioDTO toDTO(Usuario entity){
        if (entity==null) {
            return null;
        }
        UsuarioDTO dto= new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setRol(entity.getRol()!=null ? entity.getRol().name():null);
        dto.setFechaRegistro(entity.getFechaRegistro());

        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto){
        if(dto==null){
            return null;
        }

        Usuario entity= new Usuario();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setTelefono(dto.getTelefono());
        entity.setRol(dto.getRol()!=null?RolNombre.valueOf(dto.getRol()):null);

        return entity;
    }
}
