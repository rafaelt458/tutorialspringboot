package com.laboratorio.springboot37.repository;

import com.laboratorio.springboot37.dto.ProductoResponse;
import com.laboratorio.springboot37.model.Producto;
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
                    SELECT new com.laboratorio.springboot37.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                        WHERE p.id = :id
                    """
    )
    Optional<ProductoResponse> findProductoById(@Param("id") Integer id);

    @Query(
            """
                    SELECT new com.laboratorio.springboot37.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                        WHERE p.nombre = :nombre
                    """
    )
    Optional<ProductoResponse> findOneByNombre(@Param("nombre") String nombre);

    @Query(
            """
                    SELECT new com.laboratorio.springboot37.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                        ORDER BY p.nombre ASC
                    """
    )
    List<ProductoResponse> findAllOrderByNombreAsc();

    @Query(
            """
                    SELECT new com.laboratorio.springboot37.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                        WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('%', :infix, '%'))
                        ORDER BY p.nombre ASC
                    """
    )
    List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(@Param("infix") String infix);

    @Query(
            """
                    SELECT new com.laboratorio.springboot37.dto.ProductoResponse(p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                        FROM Producto p
                        WHERE p.categoriaId = :categoriaId
                        ORDER BY p.nombre ASC
                    """
    )
    List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(@Param("categoriaId") Integer categoriaId);

    long countByCategoriaId(Integer categoriaId);
}