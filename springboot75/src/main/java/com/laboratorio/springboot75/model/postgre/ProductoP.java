package com.laboratorio.springboot75.model.postgre;

import com.laboratorio.springboot75.model.dto.ProductoRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
public class ProductoP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    public ProductoP(ProductoRequest request) {
        this.nombre = request.getNombre();
        this.precio = request.getPrecio();
    }
}