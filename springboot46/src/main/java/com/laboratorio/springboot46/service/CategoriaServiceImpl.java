package com.laboratorio.springboot46.service;

import com.laboratorio.springboot46.dto.CategoriaRequest;
import com.laboratorio.springboot46.dto.CategoriaResponse;
import com.laboratorio.springboot46.exception.DatabaseException;
import com.laboratorio.springboot46.exception.InvalidOperationException;
import com.laboratorio.springboot46.exception.ResourceNotFoundException;
import com.laboratorio.springboot46.model.Categoria;
import com.laboratorio.springboot46.repository.CategoriaRepository;
import com.laboratorio.springboot46.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Optional<CategoriaResponse> findCategoriaById(Integer id) {
        Optional<CategoriaResponse> categoriaResponse;

        try {
            categoriaResponse = this.categoriaRepository.findCategoriaById(id);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al consultar la categoría con Id: %d", id);
            throw new DatabaseException(message, e);
        }
        if (categoriaResponse.isEmpty()) {
            throw new ResourceNotFoundException("No existe la categoria con Id: " + id);
        }

        return categoriaResponse;
    }

    private Optional<CategoriaResponse> findByNombre(String nombre) {
        try {
            return this.categoriaRepository.findOneByNombre(nombre);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al consultar la categoría con nombre: %s", nombre);
            throw new DatabaseException(message, e);
        }
    }

    @Override
    public Optional<CategoriaResponse> findOneByNombre(String nombre) {
        Optional<CategoriaResponse> categoriaResponse = this.findByNombre(nombre);
        if (categoriaResponse.isEmpty()) {
            throw new ResourceNotFoundException("No existe la categoria con nombre: " + nombre);
        }

        return categoriaResponse;
    }

    @Override
    public List<CategoriaResponse> findAllOrderByNombreAsc() {
        try {
            return this.categoriaRepository.findAllOrderByNombreAsc();
        } catch (Exception e) {
            throw new DatabaseException("Error de base datos al recuperar la lista de categorías", e);
        }
    }

    @Override
    public List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix) {
        try {
            return this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al consultar las categorías que contienen la expresión: %s", infix);
            throw new DatabaseException(message, e);
        }
    }

    @Override
    @Transactional
    public CategoriaResponse createCategoria(CategoriaRequest request) {
        Optional<CategoriaResponse> categoriaDB = this.findByNombre(request.getNombre());
        if (categoriaDB.isPresent()) {
            return categoriaDB.get();
        }

        try {
            Categoria categoria = new Categoria(request);
            Categoria categoriaNueva = this.categoriaRepository.save(categoria);
            return new CategoriaResponse(categoriaNueva);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al crear la categoría con nombre: %s", request.getNombre());
            throw new DatabaseException(message, e);
        }
    }

    @Override
    @Transactional
    public CategoriaResponse updateCategoria(Integer id, CategoriaRequest request) {
        Optional<CategoriaResponse> categoriaDB = this.findCategoriaById(id);

        Optional<CategoriaResponse> otraCategoria = this.categoriaRepository.findOneByNombre(request.getNombre());
        if (otraCategoria.isPresent() && !categoriaDB.get().getId().equals(otraCategoria.get().getId())) {
            throw new InvalidOperationException("No se puede modificar la categoría porque existe otra con el mismo nombre");
        }

        try {
            Categoria categoria = new Categoria(id, request.getNombre());
            Categoria categoriaModificada = this.categoriaRepository.save(categoria);
            return new CategoriaResponse(categoriaModificada);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al modificar la categoría con id: %d", id);
            throw new DatabaseException(message, e);
        }
    }

    @Override
    @Transactional
    public void deleteCategoria(Integer id) {
        // Validar la existencia de la categoria
        this.findCategoriaById(id);

        long nProductos = productoRepository.countByCategoriaId(id);
        if (nProductos > 0) {
            throw new InvalidOperationException("No se puede eliminar una categoría con productos asociados");
        }

        try {
            this.categoriaRepository.deleteById(id);
        } catch (Exception e) {
            String message = String.format("Error de base de datos al eliminar la categoría con id: %d", id);
            throw new DatabaseException(message, e);
        }
    }
}