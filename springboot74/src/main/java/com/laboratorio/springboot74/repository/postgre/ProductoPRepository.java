package com.laboratorio.springboot74.repository.postgre;

import com.laboratorio.springboot74.model.postgre.ProductoP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoPRepository extends JpaRepository<ProductoP, Integer> {
}