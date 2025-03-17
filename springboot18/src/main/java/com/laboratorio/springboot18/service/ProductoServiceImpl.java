package com.laboratorio.springboot18.service;

import com.laboratorio.springboot18.dto.CategoriaResponse;
import com.laboratorio.springboot18.dto.ProductoRequest;
import com.laboratorio.springboot18.dto.ProductoResponse;
import com.laboratorio.springboot18.exception.InvalidOperationException;
import com.laboratorio.springboot18.exception.ResourceNotFoundException;
import com.laboratorio.springboot18.model.Producto;
import com.laboratorio.springboot18.repository.ProductoRepository;
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
        return this.productoRepository.findProductoById(id);
    }

    @Override
    public Optional<ProductoResponse> findOneByNombre(String nombre) {
        return this.productoRepository.findOneByNombre(nombre);
    }

    @Override
    public List<ProductoResponse> findAllOrderByNombreAsc() {
        return this.productoRepository.findAllOrderByNombreAsc();
    }

    @Override
    public List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix) {
        return this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
    }

    @Override
    public List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer categoriaId) {
        return this.productoRepository.findByCategoriaIdOrderByNombreAsc(categoriaId);
    }

    @Override
    @Transactional
    public ProductoResponse createProducto(ProductoRequest request) {
        Optional<ProductoResponse> productoDB = this.findOneByNombre(request.getNombre());
        if (productoDB.isPresent()) {
            return productoDB.get();
        }

        Optional<CategoriaResponse> categoriaDB = this.categoriaService.findCategoriaById(request.getCategoriaId());
        if (categoriaDB.isEmpty()) {
            throw new ResourceNotFoundException("No existe la categoría indicada, no se puede crear el producto");
        }

        Producto producto = new Producto(request);
        Producto productoNuevo = this.productoRepository.save(producto);

        return new ProductoResponse(productoNuevo);
    }

    @Override
    @Transactional
    public ProductoResponse updateProducto(Integer id, ProductoRequest request) {
        Optional<ProductoResponse> productoDB = this.findProductoById(id);
        if (productoDB.isEmpty()) {
            throw new ResourceNotFoundException("No se puede efectuar la modificación, el producto no existe");
        }

        Optional<ProductoResponse> otroProducto = this.productoRepository.findOneByNombre(request.getNombre());
        if (otroProducto.isPresent() && !productoDB.get().getCodigo().equals(otroProducto.get().getCodigo())) {
            throw new InvalidOperationException("No se puede modificar el producto porque existe otro con el mismo nombre");
        }

        Optional<CategoriaResponse> categoriaDB = this.categoriaService.findCategoriaById(request.getCategoriaId());
        if (categoriaDB.isEmpty()) {
            throw new ResourceNotFoundException("No existe la categoría indicada, no se puede modificar el producto");
        }

        Producto producto = new Producto(productoDB.get(), request);
        Producto productoModificado = this.productoRepository.save(producto);

        return new ProductoResponse(productoModificado);
    }

    @Override
    @Transactional
    public boolean deleteProducto(Integer id) {
        Optional<ProductoResponse> productoDB = this.findProductoById(id);
        if (productoDB.isEmpty()) {
            return false;
        }

        this.productoRepository.deleteById(id);

        return true;
    }
}
