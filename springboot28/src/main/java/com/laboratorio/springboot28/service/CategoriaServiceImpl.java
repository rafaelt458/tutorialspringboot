package com.laboratorio.springboot28.service;

import com.laboratorio.springboot28.dto.CategoriaPage;
import com.laboratorio.springboot28.dto.CategoriaRequest;
import com.laboratorio.springboot28.dto.CategoriaResponse;
import com.laboratorio.springboot28.exception.InvalidOperationException;
import com.laboratorio.springboot28.exception.ResourceNotFoundException;
import com.laboratorio.springboot28.model.Categoria;
import com.laboratorio.springboot28.repository.CategoriaRepository;
import com.laboratorio.springboot28.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return this.categoriaRepository.findCategoriaById(id);
    }

    @Override
    public Optional<CategoriaResponse> findOneByNombre(String nombre) {
        return this.categoriaRepository.findOneByNombre(nombre);
    }

    @Override
    public List<CategoriaResponse> findAllOrderByNombreAsc() {
        Sort sort = Sort.by("nombre").ascending();
        return this.categoriaRepository.findAllCategoria(sort);
    }

    @Override
    public List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix) {
        Sort.Order order = Sort.Order.by("nombre")
                .with(Sort.Direction.ASC)
                .ignoreCase();
        Sort sort = Sort.by(order);
        return this.categoriaRepository.findByNombreContainingIgnoreCase(infix, sort);
    }

    @Override
    public CategoriaPage findCategoriaPage(int pageNumber, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<CategoriaResponse> pagina = this.categoriaRepository.findCategoriaPage(pageable);
        return new CategoriaPage(pagina.getContent(), pageNumber, pageSize, pagina.getTotalPages(), pagina.getTotalElements(), pagina.isLast());
    }

    @Override
    @Transactional
    public CategoriaResponse createCategoria(CategoriaRequest request) {
        Optional<CategoriaResponse> categoriaDB = this.findOneByNombre(request.getNombre());
        if (categoriaDB.isPresent()) {
            return categoriaDB.get();
        }

        Categoria categoria = new Categoria(request);
        Categoria categoriaNueva = this.categoriaRepository.save(categoria);

        return new CategoriaResponse(categoriaNueva);
    }

    @Override
    @Transactional
    public CategoriaResponse updateCategoria(Integer id, CategoriaRequest request) {
        Optional<CategoriaResponse> categoriaDB = this.findCategoriaById(id);
        if (categoriaDB.isEmpty()) {
            throw new ResourceNotFoundException("No se puede efectuar la modificación, la categoria no existe");
        }

        Optional<CategoriaResponse> otraCategoria = this.categoriaRepository.findOneByNombre(request.getNombre());
        if (otraCategoria.isPresent() && !categoriaDB.get().getId().equals(otraCategoria.get().getId())) {
            throw new InvalidOperationException("No se puede modificar la categoría porque existe otra con el mismo nombre");
        }

        Categoria categoria = new Categoria(id, request.getNombre());
        Categoria categoriaModificada = this.categoriaRepository.save(categoria);

        return new CategoriaResponse(categoriaModificada);
    }

    @Override
    @Transactional
    public boolean deleteCategoria(Integer id) {
        Optional<CategoriaResponse> categoriaDB = this.findCategoriaById(id);
        if (categoriaDB.isEmpty()) {
            return false;
        }

        long nProductos = productoRepository.countByCategoriaId(id);
        if (nProductos > 0) {
            throw new InvalidOperationException("No se puede eliminar una categoría con productos asociados");
        }

        this.categoriaRepository.deleteById(id);

        return true;
    }
}