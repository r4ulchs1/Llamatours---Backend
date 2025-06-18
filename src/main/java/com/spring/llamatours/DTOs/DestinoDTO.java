package com.spring.llamatours.DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
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
public class DestinoDTO {
    private Long id;
    @NotBlank(message = "El título no puede estar vacío.")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres.")
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres.")
    private String descripcion;

    @NotBlank(message = "Las actividades no pueden estar vacías.")
    @Size(min = 5, max = 500, message = "Las actividades deben tener entre 5 y 500 caracteres.")
    private String actividades;

    @NotNull(message = "El precio no puede ser nulo.")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero.")
    private BigDecimal precio;

    @NotNull(message = "La fecha de inicio no puede ser nula.")
    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro.")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin no puede ser nula.")
    @Future(message = "La fecha de fin debe ser una fecha futura.") //solo fechas en el futuro
    
    private LocalDate fechaFin;

    @NotNull(message = "Los días de duración no pueden ser nulos.")
    @Min(value = 1, message = "La duración debe ser de al menos 1 día.")
    private Integer dias;

    @NotNull(message = "Los cupos disponibles no pueden ser nulos.")
    @Min(value = 0, message = "Los cupos disponibles no pueden ser negativos.")
    private Integer cuposDisponibles;

    @NotBlank(message = "El departamento no puede estar vacío.")
    private String departamento;

    @NotBlank(message = "La provincia no puede estar vacía.")
    @Size(min = 3, max = 50, message = "La provincia debe tener entre 3 y 50 caracteres.")
    private String provincia;

    @Size(max = 255, message = "La URL de la imagen 1 no puede exceder los 255 caracteres.")
    private String urlimg1;
    @Size(max = 255, message = "La URL de la imagen 2 no puede exceder los 255 caracteres.")
    private String urlimg2;
    @Size(max = 255, message = "La URL de la imagen 3 no puede exceder los 255 caracteres.")
    private String urlimg3;
    @Size(max = 255, message = "La URL de la imagen 4 no puede exceder los 255 caracteres.")
    private String urlimg4;
}
