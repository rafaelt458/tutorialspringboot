package com.laboratorio.springboot74.repository.mysql;

import com.laboratorio.springboot74.model.mysql.ProductoM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoMRepository extends JpaRepository<ProductoM, Integer> {
}