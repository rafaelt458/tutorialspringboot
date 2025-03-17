package com.laboratorio.springboot18.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.springboot18.controller.CategoriaController;
import com.laboratorio.springboot18.dto.CategoriaRequest;
import com.laboratorio.springboot18.dto.CategoriaResponse;
import com.laboratorio.springboot18.exception.InvalidOperationException;
import com.laboratorio.springboot18.exception.ResourceNotFoundException;
import com.laboratorio.springboot18.service.CategoriaService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = CategoriaController.class)
class CategoriaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Test
    void testFindCategoriaById() throws Exception {
        int id = 1;
        CategoriaResponse categoria = new CategoriaResponse(1, "Categoría 1");
        when(this.categoriaService.findCategoriaById(id)).thenReturn(Optional.of(categoria));

        this.mockMvc.perform(get("/api/categorias/find")
                    .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Categoría 1"));
    }

    @Test
    void testFindCategoriaByNombre() throws Exception {
        String nombre = "Categoría 1";
        CategoriaResponse categoria = new CategoriaResponse(1, "Categoría 1");
        when(this.categoriaService.findOneByNombre(nombre)).thenReturn(Optional.of(categoria));

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("nombre", nombre))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value(nombre));
    }

    @Test
    void testFindCategoriaNotFound() throws Exception {
        int id = 1;
        when(this.categoriaService.findCategoriaById(id)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se ha encontrado la categoría buscada"));
    }

    @Test
    void testFindCategoriaWithoutParams() throws Exception {
        this.mockMvc.perform(get("/api/categorias/find"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    void testFindCategoriaWithTwoParams() throws Exception {
        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", "1")
                        .param("nombre", "Categoría 1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    void testFindAll() throws Exception {
        List<CategoriaResponse> categorias = List.of(
                new CategoriaResponse(1, "Categoría 1"),
                new CategoriaResponse(2, "Categoría 2")
        );
        when(this.categoriaService.findAllOrderByNombreAsc()).thenReturn(categorias);

        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testFindAllNoContent() throws Exception {
        when(this.categoriaService.findAllOrderByNombreAsc()).thenReturn(List.of());

        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByNombreContaining() throws Exception {
        String infix = "Tego";
        List<CategoriaResponse> categorias = List.of(
                new CategoriaResponse(1, "Categoría 1"),
                new CategoriaResponse(2, "Categoría 2")
        );
        when(this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix)).thenReturn(categorias);

        this.mockMvc.perform(get("/api/categorias/" + infix))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testFindByNombreContainingNoContent() throws Exception {
        String infix = "Tego";
        when(this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix)).thenReturn(List.of());

        this.mockMvc.perform(get("/api/categorias/" + infix))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreate() throws Exception {
        CategoriaRequest request = new CategoriaRequest("Categoría nueva");
        CategoriaResponse categoria = new CategoriaResponse(1, "Categoría nueva");
        when(this.categoriaService.createCategoria(any(CategoriaRequest.class))).thenReturn(categoria);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/categorias")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Categoría nueva"));
    }

    @Test
    void testUpdate() throws Exception {
        int id = 1;
        CategoriaRequest request = new CategoriaRequest("Categoría modificada");
        CategoriaResponse categoria = new CategoriaResponse(id, "Categoría modificada");
        when(this.categoriaService.updateCategoria(anyInt(), any(CategoriaRequest.class))).thenReturn(categoria);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Categoría modificada"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        int id = 1;
        CategoriaRequest request = new CategoriaRequest("Categoría modificada");
        when(this.categoriaService.updateCategoria(anyInt(), any(CategoriaRequest.class))).thenThrow(new ResourceNotFoundException("Categoría no existe"));
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Categoría no existe"));
    }

    @Test
    void testUpdateMismoNombre() throws Exception {
        int id = 1;
        CategoriaRequest request = new CategoriaRequest("Categoría modificada");
        when(this.categoriaService.updateCategoria(anyInt(), any(CategoriaRequest.class))).thenThrow(new InvalidOperationException("Existe una categoría con el mismo nombre"));
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Existe una categoría con el mismo nombre"));
    }

    @Test
    void testDelete() throws Exception {
        int id = 1;
        when(this.categoriaService.deleteCategoria(anyInt())).thenReturn(true);

        this.mockMvc.perform(delete("/api/categorias/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Se ha eliminado correctamente la categoría con id: 1"));
    }

    @Test
    void testDeleteNotFound() throws Exception {
        int id = 1;
        when(this.categoriaService.deleteCategoria(anyInt())).thenReturn(false);

        this.mockMvc.perform(delete("/api/categorias/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe un categoría con id: 1"));
    }

    @Test
    void testDeleteWithProductos() throws Exception {
        int id = 1;
        when(this.categoriaService.deleteCategoria(anyInt())).thenThrow(new InvalidOperationException("La categoría tiene productos"));

        this.mockMvc.perform(delete("/api/categorias/" + id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La categoría tiene productos"));
    }
}
