package com.laboratorio.springboot18.unit.repository;

import com.laboratorio.springboot18.dto.CategoriaResponse;
import static org.junit.jupiter.api.Assertions.*;

import com.laboratorio.springboot18.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRepositoryTest {
    @Autowired
    private CategoriaRepository categoriaRepository;


    @Test
    void findCategoriaByIdTest() {
        Integer id = 2;

        CategoriaResponse response = this.categoriaRepository.findCategoriaById(id).get();

        assertEquals(id, response.getId());
        assertEquals("Categoría 2", response.getNombre());
    }

    @Test
    void findOneByNombreTest() {
        String nombre = "Categoría 3";

        CategoriaResponse response = this.categoriaRepository.findOneByNombre(nombre).get();

        assertEquals(3, response.getId());
        assertEquals(nombre, response.getNombre());
    }

    @Test
    void findAllOrderByNombreAscTest() {
        List<CategoriaResponse> categorias = this.categoriaRepository.findAllOrderByNombreAsc();

        assertEquals(3, categorias.size());
    }

    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAsc() {
        String infix = "TeGo";

        List<CategoriaResponse> categorias = this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);

        assertEquals(3, categorias.size());
    }
}