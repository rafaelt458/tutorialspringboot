package com.laboratorio.springboot75.service.impl;

import com.laboratorio.springboot75.model.dto.ProductoRequest;
import com.laboratorio.springboot75.model.dto.ProductoResponse;
import com.laboratorio.springboot75.model.mysql.ProductoM;
import com.laboratorio.springboot75.repository.mysql.ProductoMRepository;
import com.laboratorio.springboot75.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productoMServiceImpl")
@RequiredArgsConstructor
public class ProductoMServiceImpl implements ProductoService {
    private final ProductoMRepository repository;

    @Override
    public List<ProductoResponse> findAllProductos() {
        List<ProductoM> productos = this.repository.findAll();
        return productos.stream()
                .map(p -> new ProductoResponse(
                        p.getCodigo(), p.getNombre(), p.getPrecio()))
                .toList();
    }

    @Override
    @Transactional("mysqlTransactionManager")
    public ProductoResponse createProducto(ProductoRequest request) {
        ProductoM producto = new ProductoM(request);
        ProductoM nuevo = this.repository.save(producto);
        return new ProductoResponse(nuevo.getCodigo(), nuevo.getNombre(), nuevo.getPrecio());
    }
}