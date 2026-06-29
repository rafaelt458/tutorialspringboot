package com.laboratorio.springboot75.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductoResponse {
    private Integer codigo;
    private String nombre;
    private double precio;
}