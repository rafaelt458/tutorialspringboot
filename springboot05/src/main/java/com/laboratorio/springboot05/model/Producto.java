package com.laboratorio.springboot05.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Producto {
    private Integer codigo;
    private String nombre;
    private double precio;
}