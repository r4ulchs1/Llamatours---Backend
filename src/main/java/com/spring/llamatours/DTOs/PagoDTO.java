package com.spring.llamatours.DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Long id;
    @NotNull(message = "El ID de reservación no puede ser nulo.")
    @Min(value = 1, message = "El ID de reservación debe ser un número positivo.")
    private Long reservacionId;

    private BigDecimal monto;

    @NotBlank(message = "El método de pago no puede estar vacío.")
    private String metodoPago;

    @NotBlank(message = "El estado del pago no puede estar vacío.")
    private String estado;

    // @NotNull(message = "La fecha de pago no puede ser nula.")
    @FutureOrPresent(message = "La fecha de pago debe ser hoy o en el futuro.")
    private LocalDate fechaPago;
}
