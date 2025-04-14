package com.laboratorio.springboot29.model;

import com.laboratorio.springboot29.dto.ProductoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    public Producto(ProductoRequest request) {
        this.nombre = request.getNombre();
        this.precio = request.getPrecio();
    }
}