package com.laboratorio.springboot18.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductoRequest {
    private Integer categoriaId;
    private String nombre;
    private double precio;
}