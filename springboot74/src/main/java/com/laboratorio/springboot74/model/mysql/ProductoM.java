package com.laboratorio.springboot74.model.mysql;

import com.laboratorio.springboot74.model.dto.ProductoRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
public class ProductoM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    public ProductoM(ProductoRequest request) {
        this.nombre = request.getNombre();
        this.precio = request.getPrecio();
    }
}