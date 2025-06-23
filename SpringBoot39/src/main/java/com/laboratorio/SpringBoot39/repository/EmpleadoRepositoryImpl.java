package com.laboratorio.SpringBoot39.repository;

import com.laboratorio.SpringBoot39.model.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpleadoRepositoryImpl implements EmpleadoRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Empleado> findEmpleados(String nombre, String apellido, String departamento, Integer edad) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Empleado> cq = cb.createQuery(Empleado.class);
        Root<Empleado> root = cq.from(Empleado.class);

        List<Predicate> predicates = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("nombre")), '%' + nombre.toUpperCase() + '%'));
        }

        if (apellido != null && !apellido.isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("apellido")), "%" + apellido.toUpperCase() + "%"));
        }

        if (departamento != null && !departamento.isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("departamento")), "%" + departamento.toUpperCase() + "%"));
        }

        if (edad != null) {
            predicates.add(cb.equal(root.get("edad"), edad));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("id")));

        return em.createQuery(cq).getResultList();
    }
}