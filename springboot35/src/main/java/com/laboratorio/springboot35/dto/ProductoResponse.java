package com.laboratorio.springboot35.dto;

import com.laboratorio.springboot35.model.Producto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class ProductoResponse {
    private Integer codigo;
    private Integer categoriaId;
    private String nombre;
    private double precio;
    private LocalDate fechaIngreso;

    public ProductoResponse(Integer codigo, Integer categoriaId, String nombre, double precio, LocalDate fechaIngreso) {
        this.codigo = codigo;
        this.categoriaId = categoriaId;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaIngreso = fechaIngreso;
    }

    public ProductoResponse(Producto producto) {
        this.codigo = producto.getCodigo();
        this.categoriaId = producto.getCategoriaId();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
        this.fechaIngreso = producto.getFechaIngreso();
    }
}