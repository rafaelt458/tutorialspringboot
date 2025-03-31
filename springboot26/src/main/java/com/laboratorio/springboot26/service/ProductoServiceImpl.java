package com.laboratorio.springboot26.service;

import com.laboratorio.springboot26.dto.CategoriaResponse;
import com.laboratorio.springboot26.dto.ProductoPage;
import com.laboratorio.springboot26.dto.ProductoRequest;
import com.laboratorio.springboot26.dto.ProductoResponse;
import com.laboratorio.springboot26.exception.InvalidOperationException;
import com.laboratorio.springboot26.exception.ResourceNotFoundException;
import com.laboratorio.springboot26.model.Producto;
import com.laboratorio.springboot26.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Sort.Order order1 = Sort.Order.by("nombre")
                .with(Sort.Direction.ASC)
                .ignoreCase();
        Sort.Order order2 = Sort.Order.by("precio")
                .with(Sort.Direction.ASC);
        Sort sort = Sort.by(List.of(order1, order2));
        return this.productoRepository.findAllProducto(sort);
    }

    @Override
    public List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix) {
        Sort.Order order = Sort.Order.by("nombre")
                .with(Sort.Direction.ASC)
                .ignoreCase();
        Sort sort = Sort.by(order);
        return this.productoRepository.findByNombreContainingIgnoreCase(infix, sort);
    }

    @Override
    public List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer categoriaId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "nombre")
                .and(Sort.by(Sort.Direction.ASC, "precio"));
        return this.productoRepository.findByCategoriaId(categoriaId, sort);
    }

    @Override
    public ProductoPage findProductoPage(int pageNumber, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<ProductoResponse> pagina = this.productoRepository.findProductoPage(pageable);
        return new ProductoPage(pagina.getContent(), pageNumber, pageSize, pagina.getTotalPages(), pagina.getTotalElements(), pagina.isLast());
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
