package com.spring.llamatours.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservacionDTO {
    private Long id;
    private Long usuarioId;
    private Long destinoId;
    private LocalDate fechaReserva;
    private Integer cantidadPersonas;
    private String nombresPasajeros;
    private String estado;
}
