package com.spring.llamatours.DTOs;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservacionDTO {
    private Long id;

    @NotNull(message = "El ID de usuario no puede ser nulo.")
    @Min(value = 1, message = "El ID de usuario debe ser un número positivo.")
    private Long usuarioId;

    @NotNull(message = "El ID de destino no puede ser nulo.")
    @Min(value = 1, message = "El ID de destino debe ser un número positivo.")
    private Long destinoId;

    @NotNull(message = "La fecha de reserva no puede ser nula.")
    @FutureOrPresent(message = "La fecha de reserva debe ser hoy o en el futuro.")
    private LocalDate fechaReserva;

    @NotNull(message = "La cantidad de personas no puede ser nula.")
    @Min(value = 1, message = "Debe haber al menos 1 persona en la reserva.")
    private Integer cantidadPersonas;

    @NotBlank(message = "Los nombres de los pasajeros no pueden estar vacíos.")
    @Size(min = 5, max = 500, message = "Los nombres de los pasajeros deben tener entre 5 y 500 caracteres.")
    private String nombresPasajeros;
    private String estado;
}
