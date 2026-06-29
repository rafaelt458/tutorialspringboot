package com.laboratorio.springboot75.service.impl;

import com.laboratorio.springboot75.model.dto.ProductoRequest;
import com.laboratorio.springboot75.model.dto.ProductoResponse;
import com.laboratorio.springboot75.model.postgre.ProductoP;
import com.laboratorio.springboot75.repository.postgre.ProductoPRepository;
import com.laboratorio.springboot75.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Service("productoPServiceImpl")
@RequiredArgsConstructor
public class ProductoPServiceImpl implements ProductoService {
    private final ProductoPRepository repository;

    @Override
    public List<ProductoResponse> findAllProductos() {
        List<ProductoP> productos = this.repository.findAll();
        return productos.stream()
                .map(p -> new ProductoResponse(
                        p.getCodigo(), p.getNombre(), p.getPrecio())
                )
                .toList();

    }

    @Override
    @Transactional("postgreTransactionManager")
    public ProductoResponse createProducto(ProductoRequest request) {
        ProductoP producto = new ProductoP(request);
        ProductoP nuevo = this.repository.save(producto);
        return new ProductoResponse(nuevo.getCodigo(), nuevo.getNombre(), nuevo.getPrecio());
    }
}