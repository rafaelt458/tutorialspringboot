package com.laboratorio.springboot18.integration.service;

import com.laboratorio.springboot18.dto.CategoriaRequest;
import com.laboratorio.springboot18.dto.CategoriaResponse;
import com.laboratorio.springboot18.exception.InvalidOperationException;
import com.laboratorio.springboot18.exception.ResourceNotFoundException;
import com.laboratorio.springboot18.service.CategoriaService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoriaServiceTest {
    @Autowired
    private CategoriaService categoriaService;

    private static Integer createdId;

    @Test
    @Order(1)
    void testFindCategoriaById_CategoriaExists() {
        Integer id = 2;

        Optional<CategoriaResponse> categoria = this.categoriaService.findCategoriaById(id);

        assertTrue(categoria.isPresent());
        assertEquals("Categoría 2", categoria.get().getNombre());
    }

    @Test
    @Order(2)
    void testFindCategoriaById_CategoriaNotFound() {
        Integer id = 12;

        Optional<CategoriaResponse> categoria = this.categoriaService.findCategoriaById(id);

        assertTrue(categoria.isEmpty());
    }

    @Test
    @Order(3)
    void testFindOneByNombre_CategoriaExists() {
        String nombre = "Categoría 3";

        Optional<CategoriaResponse> categoria = this.categoriaService.findOneByNombre(nombre);

        assertTrue(categoria.isPresent());
        assertEquals(3, categoria.get().getId());
    }

    @Test
    @Order(4)
    void testFindOneByNombre_CategoriaNotFount() {
        String nombre = "Categoría 5";

        Optional<CategoriaResponse> categoria = this.categoriaService.findOneByNombre(nombre);

        assertTrue(categoria.isEmpty());
    }

    @Test
    @Order(5)
    void testFindAllOrderByNombreAsc() {
        List<CategoriaResponse> categorias = this.categoriaService.findAllOrderByNombreAsc();

        assertEquals(3, categorias.size());
    }

    @Test
    @Order(6)
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc() {
        String infix = "íA 3";

        List<CategoriaResponse> categorias = this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);

        assertEquals(1, categorias.size());
    }

    @Test
    @Order(7)
    void testCreateCategoria_CategoriaCreated() {
        CategoriaRequest request = new CategoriaRequest("Categoria 4");

        CategoriaResponse categoria = this.categoriaService.createCategoria(request);
        createdId = categoria.getId();
        System.out.println(createdId);

        assertNotNull(categoria);
        assertTrue(categoria.getId() > 3);
    }
    @Test
    @Order(8)
    void testCreateCategoria_ReturnExisting() {
        CategoriaRequest request = new CategoriaRequest("Categoría 2");

        CategoriaResponse categoria = this.categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals(2, categoria.getId());
    }

    @Test
    @Order(9)
    void testUpdateCategoria_CategoriaUpdated() {
        Integer id = createdId;
        CategoriaRequest request = new CategoriaRequest("Categoría 4");

        CategoriaResponse categoria = this.categoriaService.updateCategoria(id, request);

        assertNotNull(categoria);
        assertEquals(createdId, categoria.getId());
    }

    @Test
    @Order(10)
    void testUpdateCategoria_CategoriaNotFound() {
        Integer id = 16;
        CategoriaRequest request = new CategoriaRequest("Categoría 16");

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
            this.categoriaService.updateCategoria(id, request));

        assertEquals("No se puede efectuar la modificación, la categoria no existe", exception.getMessage());
    }

    @Test
    @Order(11)
    void testUpdateCategoria_DuplicatedName() {
        Integer id = 1;
        CategoriaRequest request = new CategoriaRequest("Categoría 2");

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () ->
            this.categoriaService.updateCategoria(id, request));

        assertEquals("No se puede modificar la categoría porque existe otra con el mismo nombre", exception.getMessage());
    }

    @Test
    @Order(12)
    void testDeleteCategoria_CategoriaDeleted() {
        Integer id = createdId;

        boolean result = this.categoriaService.deleteCategoria(id);

        assertTrue(result);
    }

    @Test
    @Order(13)
    void testDeleteCategoria_CategoriaNotFound() {
        Integer id = createdId;

        boolean result = this.categoriaService.deleteCategoria(id);

        assertFalse(result);
    }

    @Test
    @Order(14)
    void testDeleteCategoria_HasProductos() {
        Integer id = 2;

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () ->
            this.categoriaService.deleteCategoria(id));

        assertEquals("No se puede eliminar una categoría con productos asociados", exception.getMessage());
    }
}