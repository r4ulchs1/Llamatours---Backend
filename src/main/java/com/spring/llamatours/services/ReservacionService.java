package com.spring.llamatours.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.llamatours.DTOs.ReservacionDTO;
import com.spring.llamatours.enums.EstadoReserva;
import com.spring.llamatours.mapper.ReservacionMapper;
import com.spring.llamatours.model.Destino;
import com.spring.llamatours.model.Reservacion;
import com.spring.llamatours.model.Usuario;
import com.spring.llamatours.repos.DestinoRepo;
import com.spring.llamatours.repos.ReservacionRepo;
import com.spring.llamatours.repos.UsuarioRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ReservacionService {
    private final ReservacionRepo reservacionRepo;
    private final ReservacionMapper reservacionMapper;

    private final UsuarioRepo usuarioRepo;
    private final DestinoRepo destinoRepo;

    @Transactional
    public List<ReservacionDTO> findAllReservaciones(){
        return reservacionRepo.findAll().stream().map(reservacionMapper::toDTO).collect(Collectors.toList());
    }
    
    @Transactional
    public Optional<ReservacionDTO> findReservacionById(Long id){
        return reservacionRepo.findById(id).map(reservacionMapper::toDTO);
    }

    @Transactional
    public ReservacionDTO saveReservacion(ReservacionDTO reservacionDTO){
        Reservacion reservacion= reservacionMapper.toEntity(reservacionDTO);

        //bucar usuario
        Usuario usuario= usuarioRepo.findById(reservacionDTO.getUsuarioId())
                                    .orElseThrow(()-> new RuntimeException("Usuario no encontrado para la reservacion"));
        reservacion.setUsuario(usuario);

        //buscar destino
        Destino destino= destinoRepo.findById(reservacionDTO.getDestinoId())
                                    .orElseThrow(()-> new RuntimeException("Destino no encontrado para la reservacion"));
        reservacion.setDestino(destino);

        reservacion.setFechaReserva(LocalDate.now());
        reservacion.setEstado(EstadoReserva.PENDIENTE);
        Reservacion savedReservacion=reservacionRepo.save(reservacion);

        //actualizar cupos disponibles del DESTINO
        
        int cupos= destino.getCuposDisponibles()-reservacion.getCantidadPersonas();
        if (cupos<0){
            throw new RuntimeException("No hay suficientes cupos");
        }
        destino.setCuposDisponibles(cupos);
        destinoRepo.save(destino);
        return reservacionMapper.toDTO(savedReservacion);
    }

    @Transactional
    public ReservacionDTO updateReservacion(Long id,ReservacionDTO reservacionDTO){
        Reservacion reservacionExiste= reservacionRepo.findById(id)
            .orElseThrow(()-> new RuntimeException("Reservacion no encontrada con ID"));
        
        reservacionExiste.setFechaReserva(reservacionDTO.getFechaReserva());
        reservacionExiste.setCantidadPersonas(reservacionDTO.getCantidadPersonas());
        reservacionExiste.setNombresPasajeros(reservacionDTO.getNombresPasajeros());
        reservacionExiste.setEstado(reservacionDTO.getEstado()!=null?EstadoReserva.valueOf(reservacionDTO.getEstado()):null);

        Reservacion updatedReservacion=reservacionRepo.save(reservacionExiste);
        return reservacionMapper.toDTO(updatedReservacion);
    }

    @Transactional
    public boolean deleteReservacionById(Long id){
        if (reservacionRepo.existsById(id)) {
            Reservacion reservacion= reservacionRepo.findById(id).orElseThrow(()-> new RuntimeException("Error al encontrar reservacion para revertir los cupos"));
            Destino destino= reservacion.getDestino();
            destino.setCuposDisponibles(destino.getCuposDisponibles()+reservacion.getCantidadPersonas());
            destinoRepo.save(destino);
            
            reservacionRepo.deleteById(id);
            return true;
        }
        return false;
    }

    ////////////////////////////////
    
    @Transactional
    public List<ReservacionDTO> findReservacionByUsuarioId(Long usuarioId){
        return reservacionRepo.findByUsuarioId(usuarioId).stream().map(reservacionMapper::toDTO).collect(Collectors.toList());
    }
    
    @Transactional
    public List<ReservacionDTO> findReservacionByDestinoId(Long destinoId){
        return reservacionRepo.findByDestinoId(destinoId).stream().map(reservacionMapper::toDTO).collect(Collectors.toList());
    }

    public ReservacionDTO updateEstadoReserva(Long reservacionId, EstadoReserva estado){
        Reservacion reservacion=reservacionRepo.findById(reservacionId)
            .orElseThrow(()-> new RuntimeException("Reservacion no encontrada."));
        reservacion.setEstado(estado);
        // return reservacionRepo.save(reservacion);
        return reservacionMapper.toDTO(reservacionRepo.save(reservacion));
    }
}
