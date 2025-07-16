package com.spring.llamatours.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.llamatours.DTOs.UsuarioDTO;
import com.spring.llamatours.enums.RolNombre;
import com.spring.llamatours.mapper.UsuarioMapper;
import com.spring.llamatours.model.Usuario;
import com.spring.llamatours.repos.UsuarioRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepo usuarioRepo;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public List<UsuarioDTO> findAllUsuarios(){
        return usuarioRepo.findAll().stream().map(usuarioMapper::toDTO).collect(Collectors.toList());
    }
    
    @Transactional
    public Optional<UsuarioDTO> findUsuarioById(Long id){
        return usuarioRepo.findById(id).map(usuarioMapper::toDTO);
    }
    
    @Transactional
    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO){
        if (usuarioRepo.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("El correo ya esta registrado");
        }
        if (usuarioRepo.findByTelefono(usuarioDTO.getTelefono()).isPresent()) {
            throw new DataIntegrityViolationException("El telefono ya esta registrado");
        }
        
        Usuario usuario= usuarioMapper.toEntity(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setRol(RolNombre.USUARIO); //por defecto
        usuario.setFechaRegistro(LocalDateTime.now());

        Usuario savedUsuario=usuarioRepo.save(usuario);

        return usuarioMapper.toDTO(savedUsuario);
    }

    @Transactional
    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO){
        Usuario usuarioExiste= usuarioRepo.findById(id)
        .orElseThrow(()-> new DataIntegrityViolationException("Usuario no encontrado."));

        if (!usuarioExiste.getEmail().equals(usuarioDTO.getEmail()) && usuarioRepo.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("El correo ya esta registrado.");
        }
        if (!usuarioExiste.getTelefono().equals(usuarioDTO.getTelefono()) && usuarioRepo.findByTelefono(usuarioDTO.getTelefono()).isPresent()) {
            throw new DataIntegrityViolationException("El telefono ya esta registrado.");
        }

        usuarioExiste.setNombre(usuarioDTO.getNombre());
        usuarioExiste.setEmail(usuarioDTO.getEmail());
        usuarioExiste.setTelefono(usuarioDTO.getTelefono());
        
         if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isBlank()) {
            usuarioExiste.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        }
        Usuario updatedUsuario=usuarioRepo.save(usuarioExiste);
        

        return usuarioMapper.toDTO(updatedUsuario);
    }
    
    @Transactional
    public boolean deleteUsuarioById(Long id){
        if (usuarioRepo.existsById(id)) {
            usuarioRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<UsuarioDTO> findUsuarioByCorreo(String correo) {
        return usuarioRepo.findByEmail(correo)
                .map(usuarioMapper::toDTO);
    }
}
