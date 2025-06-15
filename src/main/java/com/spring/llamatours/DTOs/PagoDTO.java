package com.spring.llamatours.DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Long id;
    private Long reservacionId;
    private BigDecimal monto;
    private String metodoPago;
    private String estado;
    private LocalDate fechaPago;
}
