package com.laboratorio.springboot18.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.springboot18.dto.CategoriaRequest;
import com.laboratorio.springboot18.dto.CategoriaResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoriaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static Integer createdId;

    @Test
    @Order(1)
    void testFindCategoriaById() throws Exception {
        int id = 1;

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Categoría 1"));
    }

    @Test
    @Order(2)
    void testFindCategoriaByNombre() throws Exception {
        String nombre = "Categoría 1";

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("nombre", nombre))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value(nombre));
    }

    @Test
    @Order(3)
    void testFindCategoriaNotFound() throws Exception {
        int id = 10;

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se ha encontrado la categoría buscada"));
    }

    @Test
    @Order(4)
    void testFindCategoriaWithoutParams() throws Exception {
        this.mockMvc.perform(get("/api/categorias/find"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    @Order(5)
    void testFindCategoriaWithTwoParams() throws Exception {
        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", "1")
                        .param("nombre", "Categoría 1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La búsqueda debe tener un parámetro"));
    }

    @Test
    @Order(6)
    void testFindAll() throws Exception {
        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @Order(7)
    void testFindByNombreContaining() throws Exception {
        String infix = "Tego";

        this.mockMvc.perform(get("/api/categorias/" + infix))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @Order(8)
    void testFindByNombreContainingNoContent() throws Exception {
        String infix = "texto";

        this.mockMvc.perform(get("/api/categorias/" + infix))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    void testCreate() throws Exception {
        CategoriaRequest request = new CategoriaRequest("Categoría nueva");
        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = this.mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(greaterThan(3)))
                .andExpect(jsonPath("$.nombre").value("Categoría nueva"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        CategoriaResponse categoriaResponse = objectMapper.readValue(responseBody, CategoriaResponse.class);
        createdId = categoriaResponse.getId();
    }

    @Test
    @Order(10)
    void testCreate_RetunExisting() throws Exception {
        CategoriaRequest request = new CategoriaRequest("Categoría 2");
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Categoría 2"));
    }

    @Test
    @Order(11)
    void testUpdate() throws Exception {
        int id = createdId;
        CategoriaRequest request = new CategoriaRequest("Categoría modificada");
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Categoría modificada"));
    }

    @Test
    @Order(12)
    void testUpdateNotFound() throws Exception {
        int id = 10;
        CategoriaRequest request = new CategoriaRequest("Categoría modificada");
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se puede efectuar la modificación, la categoria no existe"));
    }

    @Test
    @Order(13)
    void testUpdateMismoNombre() throws Exception {
        int id = createdId;
        CategoriaRequest request = new CategoriaRequest("Categoría 2");
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se puede modificar la categoría porque existe otra con el mismo nombre"));
    }

    @Test
    @Order(14)
    void testDelete() throws Exception {
        int id = createdId;

        this.mockMvc.perform(delete("/api/categorias/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Se ha eliminado correctamente la categoría con id: %d", createdId)));
    }

    @Test
    @Order(15)
    void testDeleteNotFound() throws Exception {
        int id = 10;

        this.mockMvc.perform(delete("/api/categorias/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe un categoría con id: 10"));
    }

    @Test
    @Order(16)
    void testDeleteWithProductos() throws Exception {
        int id = 1;

        this.mockMvc.perform(delete("/api/categorias/" + id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se puede eliminar una categoría con productos asociados"));
    }
}