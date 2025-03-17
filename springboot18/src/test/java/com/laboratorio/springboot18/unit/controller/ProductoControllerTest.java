package com.laboratorio.springboot18.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.springboot18.controller.ProductoController;
import com.laboratorio.springboot18.dto.ProductoRequest;
import com.laboratorio.springboot18.dto.ProductoResponse;
import com.laboratorio.springboot18.exception.InvalidOperationException;
import com.laboratorio.springboot18.exception.ResourceNotFoundException;
import com.laboratorio.springboot18.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductoController.class)
class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Test
    void testFindProductoById() throws Exception {
        int id = 1;
        ProductoResponse producto = new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now());
        when(this.productoService.findProductoById(id)).thenReturn(Optional.of(producto));

        this.mockMvc.perform(get("/api/productos/find")
                    .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo").value(id))
                .andExpect(jsonPath("$.nombre").value("Mouse"));
    }

    @Test
    void testFindProductoByNombre() throws Exception {
        String nombre = "Mouse";
        ProductoResponse producto = new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now());
        when(this.productoService.findOneByNombre(nombre)).thenReturn(Optional.of(producto));

        this.mockMvc.perform(get("/api/productos/find")
                        .param("nombre", nombre))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value(nombre));
    }

    @Test
    void testFindProductoNotFound() throws Exception {
        String nombre = "Mouse";
        when(this.productoService.findOneByNombre(nombre)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/api/productos/find")
                        .param("nombre", nombre))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se ha encontrado el producto buscado"));
    }

    @Test
    void testFindProductoWithoutParams() throws Exception {
        this.mockMvc.perform(get("/api/productos/find"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    void testFindProductoWithTwoParams() throws Exception {
        this.mockMvc.perform(get("/api/productos/find")
                        .param("id", "1")
                        .param("nombre", "Mouse"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    void testFindAll() throws Exception {
        List<ProductoResponse> productos = List.of(
                new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now()),
                new ProductoResponse(2, 1, "Teclado", 15, LocalDate.now()),
                new ProductoResponse(3, 1, "Disco externo", 80, LocalDate.now())
        );
        when(this.productoService.findAllOrderByNombreAsc()).thenReturn(productos);

        this.mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testFindAllNocontent() throws Exception {
        when(this.productoService.findAllOrderByNombreAsc()).thenReturn(List.of());

        this.mockMvc.perform(get("/api/productos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByNombreContaining() throws Exception {
        String infix = "Exter";
        List<ProductoResponse> productos = List.of(
                new ProductoResponse(3, 1, "Disco externo", 80, LocalDate.now())
        );
        when(this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix)).thenReturn(productos);

        this.mockMvc.perform(get("/api/productos/" + infix))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testFindByNombreContainingNocontent() throws Exception {
        String infix = "Exter";
        when(this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix)).thenReturn(List.of());

        this.mockMvc.perform(get("/api/productos/" + infix))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByCategoria() throws Exception {
        int id  = 1;
        List<ProductoResponse> productos = List.of(
                new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now()),
                new ProductoResponse(2, 1, "Teclado", 15, LocalDate.now()),
                new ProductoResponse(3, 1, "Disco externo", 80, LocalDate.now())
        );
        when(this.productoService.findByCategoriaIdOrderByNombreAsc(id)).thenReturn(productos);

        this.mockMvc.perform(get("/api/productos/categoria/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testFindByCategoriaNocontent() throws Exception {
        int id = 1;
        when(this.productoService.findByCategoriaIdOrderByNombreAsc(id)).thenReturn(List.of());

        this.mockMvc.perform(get("/api/productos/categoria/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreate() throws Exception {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        ProductoResponse producto = new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now());
        when(this.productoService.createProducto(any(ProductoRequest.class))).thenReturn(producto);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value("Mouse"));
    }

    @Test
    void testCreateInexistingCategoria() throws Exception {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        when(this.productoService.createProducto(any(ProductoRequest.class))).thenThrow(new ResourceNotFoundException("No existe la categoría"));
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No existe la categoría"));
    }

    @Test
    void testUpdate() throws Exception {
        int id = 1;
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        ProductoResponse producto = new ProductoResponse(1, 1, "Mouse", 10, LocalDate.now());
        when(this.productoService.updateProducto(anyInt(), any(ProductoRequest.class))).thenReturn(producto);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/productos/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value("Mouse"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        int id = 1;
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        when(this.productoService.updateProducto(anyInt(), any(ProductoRequest.class))).thenThrow(new ResourceNotFoundException("No existe el producto"));
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/productos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe el producto"));
    }

    @Test
    void testUpdateMismoNombre() throws Exception {
        int id = 1;
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10);
        when(this.productoService.updateProducto(anyInt(), any(ProductoRequest.class))).thenThrow(new InvalidOperationException("Existe un producto con el mismo nombre"));
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/productos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Existe un producto con el mismo nombre"));
    }

    @Test
    void testDelete() throws Exception {
        int id = 1;
        when(this.productoService.deleteProducto(anyInt())).thenReturn(true);

        this.mockMvc.perform(delete("/api/productos/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Se ha eliminado correctamente el producto con id: 1"));
    }

    @Test
    void testDeleteNotFound() throws Exception {
        int id = 1;
        when(this.productoService.deleteProducto(anyInt())).thenReturn(false);

        this.mockMvc.perform(delete("/api/productos/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe un producto con id: 1"));
    }
}