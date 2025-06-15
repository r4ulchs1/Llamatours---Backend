package com.spring.llamatours.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.llamatours.model.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario,Long>{
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByTelefono(String telefono);
}
