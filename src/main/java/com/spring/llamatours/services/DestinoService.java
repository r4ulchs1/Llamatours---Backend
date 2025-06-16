package com.spring.llamatours.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.llamatours.DTOs.DestinoDTO;
import com.spring.llamatours.enums.DepartamentoNombre;
import com.spring.llamatours.mapper.DestinoMapper;
import com.spring.llamatours.model.Destino;
import com.spring.llamatours.repos.DestinoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DestinoService {
    private final DestinoRepo destinoRepo;
    private final DestinoMapper destinoMapper;

    @Transactional
    public List<DestinoDTO> findAllDestinos(){
        return destinoRepo.findAll().stream().map(destinoMapper::toDTO).collect(Collectors.toList());
    }
    
    @Transactional
    public Optional<DestinoDTO> findDestinoById(Long id){
        return destinoRepo.findById(id).map(destinoMapper::toDTO);
    }

    @Transactional
    public DestinoDTO saveDestino(DestinoDTO destinoDTO){
        Destino destino= destinoMapper.toEntity(destinoDTO);
        //no se me ocurre que poner
        Destino savedDestino= destinoRepo.save(destino);
        return destinoMapper.toDTO(savedDestino);
    }

    @Transactional
    public DestinoDTO updateDestino(Long id, DestinoDTO destinoDTO){
        Destino destinoExiste= destinoRepo.findById(id) 
            .orElseThrow(()->new RuntimeException("Destino no encontrado con esa ID"));
        //no se me ocurre que poner
        destinoExiste.setTitulo(destinoDTO.getTitulo());
        destinoExiste.setDescripcion(destinoDTO.getDescripcion());
        destinoExiste.setActividades(destinoDTO.getActividades());
        destinoExiste.setPrecio(destinoDTO.getPrecio());
        destinoExiste.setFechaInicio(destinoDTO.getFechaInicio());
        destinoExiste.setFechaFin(destinoDTO.getFechaFin());
        destinoExiste.setDias(destinoDTO.getDias());
        destinoExiste.setCuposDisponibles(destinoDTO.getCuposDisponibles());
        destinoExiste.setDepartamento(destinoDTO.getDepartamento()!=null?DepartamentoNombre.valueOf(destinoDTO.getDepartamento()):null);
        destinoExiste.setProvincia(destinoDTO.getProvincia());
        destinoExiste.setUrlimg1(destinoDTO.getUrlimg1());
        destinoExiste.setUrlimg2(destinoDTO.getUrlimg2());
        destinoExiste.setUrlimg3(destinoDTO.getUrlimg3());
        destinoExiste.setUrlimg4(destinoDTO.getUrlimg4());
        Destino updatedDestino= destinoRepo.save(destinoExiste);
        return destinoMapper.toDTO(updatedDestino);
    }

    @Transactional
    public boolean deleteDestinoById(Long id){
        if (destinoRepo.existsById(id)) {
            destinoRepo.deleteById(id);
            return true;
        }
        return false;
    }

/////////////////////////////////////////////////
    @Transactional
    public List<DestinoDTO> findDestinosByDepaNombre(DepartamentoNombre depaNombre){
        return destinoRepo.findByDepartamento(depaNombre).stream().map(destinoMapper::toDTO).collect(Collectors.toList());
    }

    //para viaje.html
    @Transactional
    public Map<DepartamentoNombre, List<DestinoDTO>> obtenerDestinosAgrupadosPorDepartamento() {
        List<DestinoDTO> destinos = findAllDestinos(); // ya convierte desde el repo con el mapper

        // Convertimos el String del DTO a enum para poder agrupar
        return destinos.stream()
                .filter(d -> d.getDepartamento() != null)
                .collect(Collectors.groupingBy(
                        d -> DepartamentoNombre.valueOf(d.getDepartamento()), // agrupamos usando el enum real
                        LinkedHashMap::new,
                        Collectors.toList()));
    }
}
