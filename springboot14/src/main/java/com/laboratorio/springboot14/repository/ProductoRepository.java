package com.laboratorio.springboot14.repository;

import com.laboratorio.springboot14.dto.ProductoDTO;
import com.laboratorio.springboot14.dto.ProductoProjection;
import com.laboratorio.springboot14.dto.ProductoRecord;
import com.laboratorio.springboot14.model.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Consultas derivadas
    Optional<Producto> findOneByNombre(String nombre);

    Optional<Producto> findOneByNombreIgnoreCase(String nombre);

    List<Producto> findByNombreContaining(String infix);

    List<Producto> findByNombreContainingIgnoreCase(String infix);

    List<Producto> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix);

    List<Producto> findByNombreStartingWithIgnoreCaseOrderByNombreDesc(String prefix);

    List<Producto> findByCategoriaIdOrderByCodigoAsc(Integer categoriaId);

    List<Producto> findByCategoriaIdAndNombreContainingIgnoreCaseOrderByCodigoAsc(Integer categoriaId, String infix);

    List<Producto> findByPrecioLessThanOrderByCodigoAsc(double precio);

    List<Producto> findByPrecioGreaterThanEqualOrderByCodigoAsc(double precio);

    List<Producto> findByFechaIngresoAfter(LocalDate date);

    List<Producto> findByFechaIngresoBefore(LocalDate date);

    List<Producto> findByFechaIngresoBetween(LocalDate dateMin, LocalDate dateMax);

    List<Producto> findTop3ByNombreContainingIgnoreCaseOrderByNombreAsc(String infix);

    Optional<Producto> findFirstByNombreContainingIgnoreCaseOrderByNombreAsc(String infix);

    List<Producto> findByCategoriaIdIn(List<Integer> ids);

    // Consultas JPQL
    @Query("""
            SELECT p
                FROM Producto p
                WHERE p.categoriaId = :categoriaId
                AND   UPPER(p.nombre) LIKE UPPER(CONCAT('%', :infix, '%')) 
                ORDER BY p.codigo ASC
            """)
    List<Producto> findByCategoriaAndNombre(@Param("categoriaId") Integer categoriaId, @Param("infix") String infix);

    @Query("""
            UPDATE Producto p SET
                p.categoriaId = :idDestino
            WHERE p.categoriaId = :idOrigen
            """)
    @Modifying
    @Transactional
    int updateCategoriaProductos(@Param("idOrigen") Integer idOrigen, @Param("idDestino") Integer idDestino);

    @Query("DELETE FROM Producto p WHERE p.categoriaId = :categoriaId")
    @Modifying
    @Transactional
    int deleteProductosByCategoria(@Param("categoriaId") Integer categoriaId);

    @Transactional
    long deleteByCategoriaId(Integer categoriaId);

    // Consultas nativas
    @Query(value = """
            SELECT *
                FROM productos
                WHERE categoria_id = :categoriaId
                AND UPPER(nombre) LIKE UPPER('%' || :infix || '%')
                ORDER BY codigo
            """, nativeQuery = true)
    List<Producto> findByCategoriaAndNombreSQL(@Param("categoriaId") Integer categoriaId, @Param("infix") String infix);

    @Query(value = """
            UPDATE productos SET
                categoria_id = :idDestino
            WHERE categoria_id = :idOrigen
            """, nativeQuery = true)
    @Modifying
    @Transactional
    int updateCategoriaProductosSQL(@Param("idOrigen") Integer idOrigen, @Param("idDestino") Integer idDestino);

    @Query(value = "DELETE FROM productos WHERE categoria_id = :categoriaId", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteProductosByCategoriaSQL(@Param("categoriaId") Integer categoriaId);

    // Proyecciones personalizadas
    @Query("""
            SELECT new com.laboratorio.springboot14.dto.ProductoDTO(p.codigo, p.nombre, p.categoria.nombre)
                FROM Producto p
                ORDER BY p.nombre ASC
            """)
    List<ProductoDTO> findListadoProductos();

    @Query("""
            SELECT new com.laboratorio.springboot14.dto.ProductoRecord(p.codigo, p.nombre, p.categoria.nombre)
                FROM Producto p
                ORDER BY p.nombre ASC
            """)
    List<ProductoRecord> findListadoProductosRecord();

    @Query("""
            SELECT p.codigo, p.nombre, p.categoria.nombre
                FROM Producto p
                ORDER BY p.nombre ASC
            """)
    List<Object[]> findListadoProductosObject();

    @Query("""
            SELECT p.codigo AS codigo, p.nombre AS nombre, p.categoria.nombre AS categoria
                FROM Producto p
                ORDER BY p.nombre ASC
            """)
    List<ProductoProjection> findListadoProductosProjection();
}