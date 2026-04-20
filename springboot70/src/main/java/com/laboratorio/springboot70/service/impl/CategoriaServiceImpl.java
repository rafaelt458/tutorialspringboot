package com.laboratorio.springboot70.service.impl;

import com.laboratorio.springboot70.dto.CategoriaResponse;
import com.laboratorio.springboot70.model.Categoria;
import com.laboratorio.springboot70.repository.CategoriaRepository;
import com.laboratorio.springboot70.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Override
    @McpTool(
            name = "find_all_categories",
            description = "Devuelve el listado de todas las categorías registradas en la base de datos del sistema"
    )
    public List<CategoriaResponse> findAllCategoria() {
        List<Categoria> categorias = categoriaRepository.findAll();

        return categorias.stream()
                .map(CategoriaResponse::new)
                .toList();
    }

    @Override
    @McpTool(
            name = "find_category_by_name",
            description = "Busca en la base de datos del sistema una categoría por su nombre y devuelve su información en caso de encontrarla, de lo contrario devuelve null"
    )
    public CategoriaResponse findCategoriaByNombre(String nombre) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findByNombre(nombre);
        return categoriaOpt.map(CategoriaResponse::new).orElse(null);
    }

    @Override
    @McpTool(
            name = "add_category",
            description = "Permite agregar una nueva categoría a la base de datos del sistema y retorna los datos de la categoría creada"
    )
    public CategoriaResponse addCategoria(
            @McpToolParam(description = "Nombre de la nueva categoría") String nombre,
            @McpToolParam(description = "Descripción de la nueva categoría") String descripcion) {
        Categoria categoria = new Categoria(nombre, descripcion);
        Categoria categoriaNueva = categoriaRepository.save(categoria);

        return new CategoriaResponse(categoriaNueva);
    }
}