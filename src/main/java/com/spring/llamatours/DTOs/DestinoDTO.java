package com.spring.llamatours.DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String actividades;
    private BigDecimal precio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer dias;
    private Integer cuposDisponibles;
    private String departamento;
    private String provincia;
    private String urlimg1;
    private String urlimg2;
    private String urlimg3;
    private String urlimg4;
}
