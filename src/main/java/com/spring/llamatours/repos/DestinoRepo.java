package com.spring.llamatours.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.llamatours.enums.DepartamentoNombre;
import com.spring.llamatours.model.Destino;

public interface DestinoRepo extends JpaRepository<Destino,Long> {
    
    List<Destino> findByDepartamento(DepartamentoNombre departamentoNombre);
    
}
