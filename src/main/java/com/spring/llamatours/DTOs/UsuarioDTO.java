package com.spring.llamatours.DTOs;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;

    @NotBlank(message = "EL nombre no puede estar vacio")
    @Size(min=3,max=50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Size(min=6, message = "La contraseña debe tener minimo 6 caracteres")
    private String password;

    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El email debe ser de un formato valido")
    @Size(max = 100,message = "El email no puede pasar de 100 caracteres")
    private String email;
    
    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe contener 9 dígitos numéricos.")
    private String telefono;

    private String rol;
    private LocalDateTime fechaRegistro;
}
