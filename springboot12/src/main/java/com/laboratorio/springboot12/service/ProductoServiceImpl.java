package com.laboratorio.springboot12.service;

import com.laboratorio.springboot12.model.Producto;
import com.laboratorio.springboot12.repository.ProductoRepository;
import com.laboratorio.springboot12.util.exception.InvalidDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;

    @Override
    public Optional<Producto> findById(Integer id) {
        return this.productoRepository.findById(id);
    }

    @Override
    public List<Producto> findAll() {
        return this.productoRepository.findAll();
    }

    @Override
    public Producto create(Producto producto) {
        if (producto.getPrecio() > 3000) {
            throw new InvalidDataException("Precio inválido");
        }

        return this.productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> update(Integer id, Producto producto) {
        if (producto.getPrecio() > 3000) {
            throw new InvalidDataException("Precio inválido");
        }

        Optional<Producto> productoAModificar = this.findById(id);
        if (productoAModificar.isEmpty()) {
            return Optional.empty();
        }

        productoAModificar.get().setNombre(producto.getNombre());
        productoAModificar.get().setPrecio(producto.getPrecio());

        return Optional.of(this.productoRepository.save(productoAModificar.get()));
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Producto> productoAEliminar = this.findById(id);
        if (productoAEliminar.isEmpty()) {
            return false;
        }

        this.productoRepository.deleteById(id);

        return true;
    }
}