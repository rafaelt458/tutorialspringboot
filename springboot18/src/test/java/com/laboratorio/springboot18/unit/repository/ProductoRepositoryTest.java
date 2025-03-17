package com.laboratorio.springboot18.unit.repository;

import com.laboratorio.springboot18.dto.ProductoResponse;
import static org.junit.jupiter.api.Assertions.*;

import com.laboratorio.springboot18.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class ProductoRepositoryTest {
    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void findProductoByIdTest() {
        Integer id = 4;

        ProductoResponse response = this.productoRepository.findProductoById(id).get();

        assertEquals(id, response.getCodigo());
        assertEquals(2, response.getCategoriaId());
    }

    @Test
    void findOneByNombreTest() {
        String nombre = "Producto 7";

        ProductoResponse response = this.productoRepository.findOneByNombre(nombre).get();

        assertEquals(nombre, response.getNombre());
        assertEquals(3, response.getCategoriaId());
    }

    @Test
    void findAllOrderByNombreAscTest() {
        List<ProductoResponse> productos = this.productoRepository.findAllOrderByNombreAsc();

        assertEquals(9, productos.size());
    }

    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest() {
        String infix = "oDUc";

        List<ProductoResponse> productos = this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);

        assertEquals(9, productos.size());
    }

    @Test
    void findByCategoriaIdOrderByNombreAscTest() {
        Integer categoriaId = 3;

        List<ProductoResponse> productos = this.productoRepository.findByCategoriaIdOrderByNombreAsc(categoriaId);

        assertEquals(3, productos.size());
    }

    @Test
    void countByCategoriaId() {
        Integer categoriaId = 3;

        long result = this.productoRepository.countByCategoriaId(categoriaId);

        assertEquals(3L, result);
    }
}