package com.example.exam_module_4.service;

import com.example.exam_module_4.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    void add(Product product);
    void edit(Product product);
    void deleteById(Long id);
    List<Product> findAll();
    Product findById(Long id);
    Page<Product> findAll(Pageable pageable);
}
