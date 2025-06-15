package com.spring.llamatours.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.spring.llamatours.enums.DepartamentoNombre;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "destinos")
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String actividades;
    private BigDecimal precio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer dias;
    private Integer cuposDisponibles;

    @Enumerated(EnumType.STRING)
    private DepartamentoNombre departamento;

    private String provincia;
    private String urlimg1;
    private String urlimg2;
    private String urlimg3;
    private String urlimg4;
}
