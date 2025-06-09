package com.laboratorio.springboot37.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductoRequest {
    @NotNull(message = "El identificador de la categoría es obligatorio")
    private Integer categoriaId;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 120, message = "El nombre del producto debe tener un máximo de 120 caracteres")
    private String nombre;

    @Positive(message = "El precio debe ser mayor que cero")
    @Digits(integer = 10, fraction = 2, message = "El precio admite un máximo de 2 decimales")
    private double precio;
}