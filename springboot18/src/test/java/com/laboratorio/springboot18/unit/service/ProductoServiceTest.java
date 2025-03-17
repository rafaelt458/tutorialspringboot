package com.laboratorio.springboot18.unit.service;

import com.laboratorio.springboot18.dto.CategoriaResponse;
import com.laboratorio.springboot18.dto.ProductoRequest;
import com.laboratorio.springboot18.dto.ProductoResponse;
import com.laboratorio.springboot18.exception.InvalidOperationException;
import com.laboratorio.springboot18.exception.ResourceNotFoundException;
import com.laboratorio.springboot18.model.Categoria;
import com.laboratorio.springboot18.model.Producto;
import com.laboratorio.springboot18.repository.ProductoRepository;
import com.laboratorio.springboot18.service.CategoriaService;
import com.laboratorio.springboot18.service.ProductoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void testFindProductoById_ProductoExists() {
        ProductoResponse productoDB = new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now());
        when(this.productoRepository.findProductoById(1)).thenReturn(Optional.of(productoDB));

        Optional<ProductoResponse> producto = this.productoService.findProductoById(1);

        assertTrue(producto.isPresent());
        assertEquals("Mouse", producto.get().getNombre());
        verify(this.productoRepository).findProductoById(1);
    }

    @Test
    void testFindProductoById_ProductoNotFound() {
        when(this.productoRepository.findProductoById(1)).thenReturn(Optional.empty());

        Optional<ProductoResponse> producto = this.productoService.findProductoById(1);

        assertTrue(producto.isEmpty());
        verify(this.productoRepository).findProductoById(1);
    }

    @Test
    void testFindOneByNombre_ProductoExists() {
        ProductoResponse productoDB = new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now());
        when(this.productoRepository.findOneByNombre("Mouse")).thenReturn(Optional.of(productoDB));

        Optional<ProductoResponse> producto = this.productoService.findOneByNombre("Mouse");

        assertTrue(producto.isPresent());
        assertEquals(1, producto.get().getCodigo());
        verify(this.productoRepository).findOneByNombre("Mouse");
    }

    @Test
    void testFindOneByNombre_ProductoNotFount() {
        when(this.productoRepository.findOneByNombre("Mouse")).thenReturn(Optional.empty());

        Optional<ProductoResponse> producto = this.productoService.findOneByNombre("Mouse");

        assertTrue(producto.isEmpty());
        verify(this.productoRepository).findOneByNombre("Mouse");
    }

    @Test
    void testFindAllOrderByNombreAsc() {
        List<ProductoResponse> productosDB = List.of(
                new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now()),
                new ProductoResponse(2, 1, "Teclado", 15, LocalDate.now()),
                new ProductoResponse(3, 1, "Disco externo", 80, LocalDate.now())
        );
        when(this.productoRepository.findAllOrderByNombreAsc()).thenReturn(productosDB);

        List<ProductoResponse> productos = this.productoService.findAllOrderByNombreAsc();

        assertFalse(productos.isEmpty());
        assertEquals(3, productos.size());
        verify(this.productoRepository).findAllOrderByNombreAsc();
    }

    @Test
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc() {
        List<ProductoResponse> productosDB = List.of(
                new ProductoResponse(2, 1, "Teclado", 15, LocalDate.now())
        );
        when((this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla"))).thenReturn(productosDB);

        List<ProductoResponse> productos = this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla");

        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        verify(this.productoRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla");
    }

    @Test
    void testFindByCategoriaIdOrderByNombreAsc() {
        List<ProductoResponse> productosDB = List.of(
                new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now()),
                new ProductoResponse(2, 1, "Teclado", 15, LocalDate.now()),
                new ProductoResponse(3, 1, "Disco externo", 80, LocalDate.now())
        );
        when(this.productoRepository.findByCategoriaIdOrderByNombreAsc(1)).thenReturn(productosDB);

        List<ProductoResponse> productos = this.productoService.findByCategoriaIdOrderByNombreAsc(1);

        assertFalse(productos.isEmpty());
        assertEquals(3, productos.size());
        verify(this.productoRepository).findByCategoriaIdOrderByNombreAsc(1);

    }

    @Test
    void testCreateProducto_CreateNew() {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "Periféricos");
        Producto productoDB = new Producto(1, 1, "Mouse", 10, LocalDate.now(), new Categoria(1, "Periféricos"));
        when(this.productoRepository.findOneByNombre(request.getNombre())).thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId())).thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.save(any(Producto.class))).thenReturn(productoDB);

        ProductoResponse producto = this.productoService.createProducto(request);

        assertNotNull(producto);
        assertEquals(1, producto.getCodigo());
        assertEquals("Mouse", producto.getNombre());
        verify(this.productoRepository).findOneByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository).save(any(Producto.class));
    }

    @Test
    void testCreateProducto_ReturnExisting() {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        ProductoResponse productoDB = new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now());
        when(this.productoRepository.findOneByNombre(request.getNombre())).thenReturn(Optional.of(productoDB));

        ProductoResponse producto = this.productoService.createProducto(request);

        assertNotNull(producto);
        assertEquals(1, producto.getCodigo());
        assertEquals("Mouse", producto.getNombre());
        verify(this.productoRepository).findOneByNombre("Mouse");
        verify(this.categoriaService, never()).findCategoriaById(1);
        verify(this.productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testCreateProducto_CategoriaNotFound() {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        when(this.productoRepository.findOneByNombre(request.getNombre())).thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                this.productoService.createProducto(request);
        });

        assertEquals("No existe la categoría indicada, no se puede crear el producto", exception.getMessage());
        verify(this.productoRepository).findOneByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testUpdateProducto_ProductoUpdated() {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        ProductoResponse productoDB = new ProductoResponse(1, 1, "Mouse", 9, LocalDate.now());
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "Periféricos");
        Producto productoModificado = new Producto(1, 1, "Mouse", 10, LocalDate.now(), new Categoria(1, "Periféricos"));
        when(this.productoRepository.findProductoById(1)).thenReturn(Optional.of(productoDB));
        when(this.productoRepository.findOneByNombre(request.getNombre())).thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId())).thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.save(any(Producto.class))).thenReturn(productoModificado);

        ProductoResponse producto = this.productoService.updateProducto(1, request);

        assertNotNull(producto);
        assertEquals(1, producto.getCodigo());
        assertEquals(10, producto.getPrecio());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findOneByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository).save(any(Producto.class));
    }

    @Test
    void testUpdateProducto_ProductoNotFound() {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        when(this.productoRepository.findProductoById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.productoService.updateProducto(1, request);
        });

        assertEquals("No se puede efectuar la modificación, el producto no existe", exception.getMessage());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository, never()).findOneByNombre("Mouse");
        verify(this.categoriaService, never()).findCategoriaById(1);
        verify(this.productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testUpdateProducto_DuplicatedName() {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        ProductoResponse productoDB1 = new ProductoResponse(1, 1, "Generic Mouse", 9, LocalDate.now());
        ProductoResponse productoDB2 = new ProductoResponse(2, 1, "Mouse", 9, LocalDate.now());
        when(this.productoRepository.findProductoById(1)).thenReturn(Optional.of(productoDB1));
        when(this.productoRepository.findOneByNombre(request.getNombre())).thenReturn(Optional.of(productoDB2));

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
           this.productoService.updateProducto(1, request);
        });

        assertEquals("No se puede modificar el producto porque existe otro con el mismo nombre", exception.getMessage());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findOneByNombre("Mouse");
        verify(this.categoriaService, never()).findCategoriaById(1);
        verify(this.productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testUpdateProducto_CategoriaNotExists() {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        ProductoResponse productoDB = new ProductoResponse(1, 1, "Mouse", 9, LocalDate.now());
        when(this.productoRepository.findProductoById(1)).thenReturn(Optional.of(productoDB));
        when(this.productoRepository.findOneByNombre(request.getNombre())).thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.productoService.updateProducto(1, request);
        });

        assertEquals("No existe la categoría indicada, no se puede modificar el producto", exception.getMessage());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findOneByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testDeleteProducto_ProductoDeleted() {
        ProductoResponse productoDB = new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now());
        when(this.productoRepository.findProductoById(1)).thenReturn(Optional.of(productoDB));

        boolean result = this.productoService.deleteProducto(1);

        assertTrue(result);
        verify(this.productoRepository).findProductoById(1);
    }

    @Test
    void testDeleteProducto_ProductoNotFound() {
        when(this.productoRepository.findProductoById(1)).thenReturn(Optional.empty());

        boolean result = this.productoService.deleteProducto(1);

        assertFalse(result);
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository, never()).deleteById(anyInt());
    }
}