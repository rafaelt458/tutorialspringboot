package com.laboratorio.SpringBoot39.repository.specification;

import com.laboratorio.SpringBoot39.model.Empleado;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class EmpleadoSpecifications {
    private EmpleadoSpecifications() {
    }

    public static Specification<Empleado> empleadoSearch(String nombre, String apellido,
                                                         String departamento, Integer edad) {
        return (((root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (nombre != null && !nombre.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.upper(root.get("nombre")), "%" + nombre.toUpperCase() + "%"));
            }

            if (apellido != null && !apellido.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.upper(root.get("apellido")), "%" + apellido.toUpperCase() + "%"));
            }

            if (departamento != null && !departamento.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.upper(root.get("departamento")), "%" + departamento.toUpperCase() + "%"));
            }

            if (edad != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("edad"), edad));
            }

            return predicate;
        }));
    }

    public static Specification<Empleado> conNombre(String nombre) {
        return (root, query, cb) -> (
                nombre == null ? null : cb.like(cb.upper(root.get("nombre")), "%" + nombre.toUpperCase() + "%")
        );
    }

    public static Specification<Empleado> conApellido(String apellido) {
        return (root, query, cb) -> (
                apellido == null ? null : cb.like(cb.upper(root.get("apellido")), "%" + apellido.toUpperCase() + "%")
        );
    }

    public static Specification<Empleado> conDepartamento(String departamento) {
        return (root, query, cb) -> (
                departamento == null ? null : cb.like(cb.upper(root.get("departamento")), "%" + departamento.toUpperCase() + "%")
        );
    }

    public static Specification<Empleado> conEdad(Integer edad) {
        return (root, query, cb) -> (
                edad == null ? null : cb.equal(root.get("edad"), edad)
        );
    }
}
