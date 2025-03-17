package com.laboratorio.springboot18.integration.service;

import com.laboratorio.springboot18.dto.ProductoRequest;
import com.laboratorio.springboot18.dto.ProductoResponse;
import com.laboratorio.springboot18.exception.InvalidOperationException;
import com.laboratorio.springboot18.exception.ResourceNotFoundException;
import com.laboratorio.springboot18.service.ProductoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductoServiceTest {
    @Autowired
    private ProductoService productoService;

    private static Integer createdId;

    @Test
    @Order(1)
    void testFindOneById_ProductoExists() {
        Integer productoId = 4;

        Optional<ProductoResponse> producto = productoService.findProductoById(productoId);

        assertTrue(producto.isPresent());
        assertEquals("Producto 4", producto.get().getNombre());
    }

    @Test
    @Order(2)
    void testFindOneById_ProductoNotFound() {
        Integer productoId = 14;

        Optional<ProductoResponse> producto = productoService.findProductoById(productoId);

        assertTrue(producto.isEmpty());
    }

    @Test
    @Order(3)
    void testFindOneByNombre_ProductoExists() {
        String nombre = "Producto 2";

        Optional<ProductoResponse> producto = this.productoService.findOneByNombre(nombre);

        assertTrue(producto.isPresent());
        assertEquals(2, producto.get().getCodigo());
    }

    @Test
    @Order(4)
    void testFindOneByNombre_ProductoNotFount() {
        String nombre = "Producto 14";

        Optional<ProductoResponse> producto = this.productoService.findOneByNombre(nombre);

        assertTrue(producto.isEmpty());
    }

    @Test
    @Order(5)
    void testFindAllOrderByNombre() {
        List<ProductoResponse> productos = this.productoService.findAllOrderByNombreAsc();

        assertEquals(9, productos.size());
    }

    @Test
    @Order(6)
    void testFindByNombreContainingIgnoreCaseOrderByNombre() {
        String infix = "DucTo";

        List<ProductoResponse> productos = this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);

        assertEquals(9, productos.size());
    }

    @Test
    @Order(7)
    void testFindByCategoriaIdOrderByNombre() {
        Integer categoriaId = 2;

        List<ProductoResponse> productos = this.productoService.findByCategoriaIdOrderByNombreAsc(categoriaId);

        assertEquals(3, productos.size());
    }

    @Test
    @Order(8)
    void testCreateProducto_CreateNew() {
        ProductoRequest request = new ProductoRequest(1, "Producto 10", 10);

        ProductoResponse producto = this.productoService.createProducto(request);
        createdId = producto.getCodigo();

        assertNotNull(producto);
        assertTrue(producto.getCodigo() > 9);
    }

    @Test
    @Order(9)
    void testCreateProducto_ReturnExisting() {
        ProductoRequest request = new ProductoRequest(1, "Producto 1", 10);

        ProductoResponse producto = this.productoService.createProducto(request);

        assertNotNull(producto);
        assertEquals(1, producto.getCodigo());
    }
    @Test
    @Order(10)
    void testCreateProducto_CategoriaNotFound() {
        ProductoRequest request = new ProductoRequest(10, "Producto 11", 11);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productoService.createProducto(request));

        assertEquals("No existe la categoría indicada, no se puede crear el producto", exception.getMessage());
    }

    @Test
    @Order(11)
    void testUpdateProducto_ProductoUpdated() {
        Integer productoId = createdId;
        ProductoRequest request = new ProductoRequest(3, "Producto 10", 10.25);

        ProductoResponse producto = this.productoService.updateProducto(productoId, request);

        assertNotNull(producto);
        assertEquals(productoId, producto.getCodigo());
    }

    @Test
    @Order(12)
    void testUpdateProducto_ProductoNotFound() {
        Integer productoId = 14;
        ProductoRequest request = new ProductoRequest(3, "Producto 14", 12.25);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                this.productoService.updateProducto(productoId, request));

        assertEquals("No se puede efectuar la modificación, el producto no existe", exception.getMessage());
    }

    @Test
    @Order(13)
    void testUpdateProducto_DuplicatedName() {
        Integer productoId = createdId;
        ProductoRequest request = new ProductoRequest(1, "Producto 1", 10.25);

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () ->
                this.productoService.updateProducto(productoId, request));

        assertEquals("No se puede modificar el producto porque existe otro con el mismo nombre", exception.getMessage());
    }

    @Test
    @Order(14)
    void testUpdateProducto_CategoriaNotExists() {
        Integer productoId = createdId;
        ProductoRequest request = new ProductoRequest(5, "Producto 10", 10.25);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                this.productoService.updateProducto(productoId, request));

        assertEquals("No existe la categoría indicada, no se puede modificar el producto", exception.getMessage());
    }

    @Test
    @Order(15)
    void testDeleteProducto_ProductoDeleted() {
        Integer productoId = createdId;

        boolean result = this.productoService.deleteProducto(productoId);

        assertTrue(result);
    }

    @Test
    @Order(16)
    void testDeleteProducto_ProductoNotFound() {
        Integer productoId = createdId;

        boolean result = this.productoService.deleteProducto(productoId);

        assertFalse(result);
    }
}