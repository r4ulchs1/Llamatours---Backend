package com.spring.llamatours.DTOs;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String password;
    private String email;
    private String telefono;
    private String rol;
    private LocalDateTime fechaRegistro;
}
