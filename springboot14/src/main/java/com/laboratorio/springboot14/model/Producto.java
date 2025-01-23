package com.laboratorio.springboot14.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "productos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "categoria_id", nullable = false)
    private Integer categoriaId;

    @Column(nullable = false, length = 120, unique = true)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", insertable = false, updatable = false)
    @JsonBackReference
    private Categoria categoria;

    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", categoriaId=" + categoriaId +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", fechaIngreso=" + fechaIngreso +
                '}';
    }
}