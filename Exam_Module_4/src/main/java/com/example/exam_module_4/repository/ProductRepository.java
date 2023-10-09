package com.example.exam_module_4.repository;

import com.example.exam_module_4.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
