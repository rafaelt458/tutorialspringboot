package com.laboratorio.springboot28.repository;

import com.laboratorio.springboot28.dto.CategoriaResponse;
import com.laboratorio.springboot28.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    @Query("""
            SELECT new com.laboratorio.springboot28.dto.CategoriaResponse(c.id, c.nombre)
                FROM Categoria c
                WHERE c.id = :id
            """
    )
    Optional<CategoriaResponse> findCategoriaById(@Param("id") Integer id);

    @Query("""
            SELECT new com.laboratorio.springboot28.dto.CategoriaResponse(c.id, c.nombre)
                FROM Categoria c
                WHERE c.nombre = :nombre
            """
    )
    Optional<CategoriaResponse> findOneByNombre(@Param("nombre") String nombre);

    @Query("""
            SELECT new com.laboratorio.springboot28.dto.CategoriaResponse(c.id, c.nombre)
                FROM Categoria c
            """
    )
    List<CategoriaResponse> findAllCategoria(Sort sort);

    @Query("""
            SELECT new com.laboratorio.springboot28.dto.CategoriaResponse(c.id, c.nombre)
                FROM Categoria c
                WHERE UPPER(c.nombre) LIKE UPPER(CONCAT('%', :infix, '%'))
            """
    )
    List<CategoriaResponse> findByNombreContainingIgnoreCase(@Param("infix") String infix, Sort sort);

    @Query("""
            SELECT new com.laboratorio.springboot28.dto.CategoriaResponse(c.id, c.nombre)
                FROM Categoria c
            """
    )
    Page<CategoriaResponse> findCategoriaPage(Pageable pageable);
}
