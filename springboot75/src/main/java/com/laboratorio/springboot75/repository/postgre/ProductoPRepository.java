package com.laboratorio.springboot75.repository.postgre;

import com.laboratorio.springboot75.model.postgre.ProductoP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoPRepository extends JpaRepository<ProductoP, Integer> {
}