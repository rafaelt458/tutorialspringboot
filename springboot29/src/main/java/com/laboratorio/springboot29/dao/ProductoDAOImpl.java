package com.laboratorio.springboot29.dao;

import com.laboratorio.springboot29.dto.ProductoRequest;
import com.laboratorio.springboot29.dto.ProductoResponse;
import com.laboratorio.springboot29.model.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductoDAOImpl implements ProductoDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductoResponse findById(Integer codigo) {
        String jpql = """
                SELECT new com.laboratorio.springboot29.dto.ProductoResponse(p.codigo, p.nombre, p.precio)
                    FROM Producto p
                    WHERE p.codigo = :codigo
                """;

        try {
         return this.entityManager.createQuery(jpql, ProductoResponse.class)
                 .setParameter("codigo", codigo)
                 .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ProductoResponse> findAll() {
        String jpql = """
                SELECT new com.laboratorio.springboot29.dto.ProductoResponse(p.codigo, p.nombre, p.precio)
                    FROM Producto p
                """;

        return this.entityManager.createQuery(jpql, ProductoResponse.class)
                .getResultList();
    }

    @Override
    public void save(ProductoRequest request) {
        Producto producto = new Producto(request);
        this.entityManager.persist(producto);
    }

    @Override
    public boolean update(Integer codigo, ProductoRequest request) {
        Producto producto = this.entityManager.find(Producto.class, codigo);
        if (producto == null) {
            return false;
        }

        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        this.entityManager.merge(producto);

        return true;
    }

    @Override
    public boolean delete(Integer codigo) {
        Producto producto = this.entityManager.find(Producto.class, codigo);
        if (producto == null) {
            return false;
        }

        this.entityManager.remove(producto);

        return true;
    }
}