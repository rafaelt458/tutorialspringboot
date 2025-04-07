package com.laboratorio.springboot28.repository;

import com.laboratorio.springboot28.dto.ProductoResponse;
import com.laboratorio.springboot28.model.Producto;
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
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    @Query(
            """
                    SELECT new com.laboratorio.springboot28.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                        WHERE p.id = :id
                    """
    )
    Optional<ProductoResponse> findProductoById(@Param("id") Integer id);

    @Query(
            """
                    SELECT new com.laboratorio.springboot28.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                        WHERE p.nombre = :nombre
                    """
    )
    Optional<ProductoResponse> findOneByNombre(@Param("nombre") String nombre);

    @Query(
            """
                    SELECT new com.laboratorio.springboot28.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                    """
    )
    List<ProductoResponse> findAllProducto(Sort sort);

    @Query(
            """
                    SELECT new com.laboratorio.springboot28.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                        WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('%', :infix, '%'))
                    """
    )
    List<ProductoResponse> findByNombreContainingIgnoreCase(@Param("infix") String infix, Sort sort);

    @Query(
            """
                    SELECT new com.laboratorio.springboot28.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                        WHERE p.categoriaId = :categoriaId
                    """
    )
    List<ProductoResponse> findByCategoriaId(@Param("categoriaId") Integer categoriaId, Sort sort);

    @Query(
            """
                    SELECT new com.laboratorio.springboot28.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                    """
    )
    Page<ProductoResponse> findProductoPage(Pageable pageable);

    long countByCategoriaId(Integer categoriaId);
}