package com.laboratorio.springboot51.service;

import com.laboratorio.springboot51.dto.CategoriaResponse;
import com.laboratorio.springboot51.dto.ProductoRequest;
import com.laboratorio.springboot51.dto.ProductoResponse;
import com.laboratorio.springboot51.exception.DatabaseException;
import com.laboratorio.springboot51.exception.InvalidOperationException;
import com.laboratorio.springboot51.exception.ResourceNotFoundException;
import com.laboratorio.springboot51.model.Producto;
import com.laboratorio.springboot51.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;

    @Override
    public Optional<ProductoResponse> findProductoById(Integer id) {
        Optional<ProductoResponse> productoResponse;
        try {
            productoResponse = this.productoRepository.findProductoById(id);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al consultar el producto con Id: %d", id);
            throw new DatabaseException(message, e);
        }
        if (productoResponse.isEmpty()) {
            throw new ResourceNotFoundException("No existe el producto con Id: " + id);
        }

        return  productoResponse;
    }

    private Optional<ProductoResponse> findByNombre(String nombre) {
        try {
            return this.productoRepository.findOneByNombre(nombre);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al consultar el producto con nombre: %s", nombre);
            throw new DatabaseException(message, e);
        }
    }

    @Override
    public Optional<ProductoResponse> findOneByNombre(String nombre) {
        Optional<ProductoResponse> productoResponse = this.findByNombre(nombre);
        if (productoResponse.isEmpty()) {
            throw new ResourceNotFoundException("No existe el producto con nombre: " + nombre);
        }

        return productoResponse;
    }

    @Override
    public List<ProductoResponse> findAllOrderByNombreAsc() {
        try {
            return this.productoRepository.findAllOrderByNombreAsc();
        } catch (Exception e) {
            throw new DatabaseException("Error de base datos al recuperar la lista de productos", e);
        }
    }

    @Override
    public List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix) {
        try {
            return this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al consultar los productos que contienen la expresión: %s", infix);
            throw new DatabaseException(message, e);
        }
    }

    @Override
    public List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer categoriaId) {
        try {
            return this.productoRepository.findByCategoriaIdOrderByNombreAsc(categoriaId);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al consultar los productos de la categoría con Id: %d", categoriaId);
            throw new DatabaseException(message, e);
        }
    }

    @Override
    @Transactional
    public ProductoResponse createProducto(ProductoRequest request) {
        Optional<ProductoResponse> productoDB = this.findOneByNombre(request.getNombre());
        if (productoDB.isPresent()) {
            return productoDB.get();
        }

        // Verifica la existencia de la categoria
        this.categoriaService.findCategoriaById(request.getCategoriaId());

        try {
            Producto producto = new Producto(request);
            Producto productoNuevo = this.productoRepository.save(producto);
            return new ProductoResponse(productoNuevo);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al crear el producto con nombre: %s", request.getNombre());
            throw new DatabaseException(message, e);
        }
    }

    @Override
    @Transactional
    public ProductoResponse updateProducto(Integer id, ProductoRequest request) {
        Optional<ProductoResponse> productoDB = this.findProductoById(id);

        Optional<ProductoResponse> otroProducto = this.productoRepository.findOneByNombre(request.getNombre());
        if (otroProducto.isPresent() && !productoDB.get().getCodigo().equals(otroProducto.get().getCodigo())) {
            throw new InvalidOperationException("No se puede modificar el producto porque existe otro con el mismo nombre");
        }

        Optional<CategoriaResponse> categoriaDB = this.categoriaService.findCategoriaById(request.getCategoriaId());
        if (categoriaDB.isEmpty()) {
            throw new ResourceNotFoundException("No existe la categoría indicada, no se puede modificar el producto");
        }

        try {
            Producto producto = new Producto(productoDB.get(), request);
            Producto productoModificado = this.productoRepository.save(producto);
            return new ProductoResponse(productoModificado);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al modificar el producto con id: %d", id);
            throw new DatabaseException(message, e);
        }
    }

    @Override
    @Transactional
    public void deleteProducto(Integer id) {
        // Validar la existencia del producto a eliminar
        this.findProductoById(id);

        try {
            this.productoRepository.deleteById(id);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al eliminar el producto con id: %d", id);
            throw new DatabaseException(message, e);
        }
    }
}
