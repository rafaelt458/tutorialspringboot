package com.laboratorio.springboot18.unit.service;

import com.laboratorio.springboot18.dto.CategoriaRequest;
import com.laboratorio.springboot18.dto.CategoriaResponse;
import com.laboratorio.springboot18.exception.InvalidOperationException;
import com.laboratorio.springboot18.exception.ResourceNotFoundException;
import com.laboratorio.springboot18.model.Categoria;
import com.laboratorio.springboot18.repository.CategoriaRepository;
import com.laboratorio.springboot18.repository.ProductoRepository;
import com.laboratorio.springboot18.service.CategoriaServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Test
    void testFindCategoriaById_CategoriaExists() {
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "Periféricos");
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.of(categoriaDB));

        Optional<CategoriaResponse> categoria = this.categoriaService.findCategoriaById(1);

        assertTrue(categoria.isPresent());
        assertEquals("Periféricos", categoria.get().getNombre());
        verify(this.categoriaRepository).findCategoriaById(1);
    }

    @Test
    void testFindCategoriaById_CategoriaNotFound() {
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.empty());

        Optional<CategoriaResponse> categoria = this.categoriaService.findCategoriaById(1);

        assertTrue(categoria.isEmpty());
        verify(this.categoriaRepository).findCategoriaById(1);
    }

    @Test
    void testFindOneByNombre_CategoriaExists() {
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "Periféricos");
        when(this.categoriaRepository.findOneByNombre("Periféricos")).thenReturn(Optional.of(categoriaDB));

        Optional<CategoriaResponse> categoria = this.categoriaService.findOneByNombre("Periféricos");

        assertTrue(categoria.isPresent());
        assertEquals(1, categoria.get().getId());
        verify(this.categoriaRepository).findOneByNombre("Periféricos");
    }

    @Test
    void testFindOneByNombre_CategoriaNotFount() {
        when(this.categoriaRepository.findOneByNombre("Periféricos")).thenReturn(Optional.empty());

        Optional<CategoriaResponse> categoria = this.categoriaService.findOneByNombre("Periféricos");

        assertTrue(categoria.isEmpty());
        verify(this.categoriaRepository).findOneByNombre("Periféricos");
    }

    @Test
    void testFindAllOrderByNombreAsc() {
        List<CategoriaResponse> categoriasDB = List.of(
                new CategoriaResponse(3, "Impresoras"),
                new CategoriaResponse(2, "Monitores"),
                new CategoriaResponse(1, "Periféricos")
        );
        when(this.categoriaRepository.findAllOrderByNombreAsc()).thenReturn(categoriasDB);

        List<CategoriaResponse> categorias = this.categoriaService.findAllOrderByNombreAsc();

        assertFalse(categorias.isEmpty());
        assertEquals(3, categorias.size());
        verify(this.categoriaRepository).findAllOrderByNombreAsc();
    }

    @Test
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc() {
        List<CategoriaResponse> categoriasDB = List.of(
                new CategoriaResponse(2, "Monitores")
        );
        when(this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc("Nito")).thenReturn(categoriasDB);

        List<CategoriaResponse> categorias = this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc("Nito");

        assertFalse(categorias.isEmpty());
        assertEquals(1, categorias.size());
        verify(this.categoriaRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc("Nito");
    }

    @Test
    void testCreateCategoria_CategoriaCreated() {
        CategoriaRequest request = new CategoriaRequest("Periféricos");
        Categoria categoriaNueva = new Categoria(1, "Periféricos");
        when(this.categoriaRepository.findOneByNombre("Periféricos")).thenReturn(Optional.empty());
        when(this.categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaNueva);

        CategoriaResponse categoria = this.categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals(1, categoria.getId());
        assertEquals("Periféricos", categoria.getNombre());
        verify(this.categoriaRepository).findOneByNombre("Periféricos");
        verify(this.categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void testCreateCategoria_ReturnExisting() {
        CategoriaRequest request = new CategoriaRequest("Periféricos");
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "Periféricos");
        when(this.categoriaRepository.findOneByNombre("Periféricos")).thenReturn(Optional.of(categoriaDB));

        CategoriaResponse categoria = this.categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals(1, categoria.getId());
        assertEquals("Periféricos", categoria.getNombre());
        verify(this.categoriaRepository).findOneByNombre("Periféricos");
        verify(this.categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void testUpdateCategoria_CategoriaUpdated() {
        CategoriaRequest request = new CategoriaRequest("Periféricos");
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "Perifericos");
        Categoria categoriaModificada = new Categoria(1, "Periféricos");
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.of(categoriaDB));
        when(this.categoriaRepository.findOneByNombre("Periféricos")).thenReturn(Optional.empty());
        when(this.categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaModificada);

        CategoriaResponse categoria = this.categoriaService.updateCategoria(1, request);

        assertNotNull(categoria);
        assertEquals("Periféricos", categoria.getNombre());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.categoriaRepository).findOneByNombre("Periféricos");
        verify(this.categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void testUpdateCategoria_CategoriaNotFound() {
        CategoriaRequest request = new CategoriaRequest("Periféricos");
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.categoriaService.updateCategoria(1, request);
        });

        assertEquals("No se puede efectuar la modificación, la categoria no existe", exception.getMessage());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.categoriaRepository, never()).findOneByNombre(anyString());
        verify(this.categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void testUpdateCategoria_DuplicatedName() {
        CategoriaRequest request = new CategoriaRequest("Periféricos");
        CategoriaResponse categoriaDB1 = new CategoriaResponse(1, "Perifericos");
        CategoriaResponse categoriaDB2 = new CategoriaResponse(2, "Periféricos");
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.of(categoriaDB1));
        when(this.categoriaRepository.findOneByNombre(request.getNombre())).thenReturn(Optional.of(categoriaDB2));

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            this.categoriaService.updateCategoria(1, request);
        });

        assertEquals("No se puede modificar la categoría porque existe otra con el mismo nombre", exception.getMessage());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.categoriaRepository).findOneByNombre("Periféricos");
        verify(this.categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void testDeleteCategoria_CategoriaDeleted() {
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "Periféricos");
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.countByCategoriaId(1)).thenReturn(0L);

        boolean result = this.categoriaService.deleteCategoria(1);

        assertTrue(result);
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.productoRepository).countByCategoriaId(1);
        verify(this.categoriaRepository).deleteById(1);
    }

    @Test
    void testDeleteCategoria_CategoriaNotFound() {
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.empty());

        boolean result = this.categoriaService.deleteCategoria(1);

        assertFalse(result);
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.productoRepository, never()).countByCategoriaId(anyInt());
        verify(this.categoriaRepository, never()).deleteById(anyInt());
    }

    @Test
    void testDeleteCategoria_HasProductos() {
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "Periféricos");
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.countByCategoriaId(1)).thenReturn(2L);

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            this.categoriaService.deleteCategoria(1);
        });

        assertEquals("No se puede eliminar una categoría con productos asociados", exception.getMessage());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.productoRepository).countByCategoriaId(1);
        verify(this.categoriaRepository, never()).deleteById(anyInt());
    }
}