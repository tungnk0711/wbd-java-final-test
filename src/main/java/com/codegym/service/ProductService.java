package com.codegym.service;

import com.codegym.model.Product;

import java.util.List;

public interface ProductService {
    Iterable<Product> findAll();

    List<Product> findByNameContaining(String name);

    void save(Product product);

    Product findById(Long id);

    void remove(Long id);

    /*Product findById(Long id);

    void save(Product product);

    void remove(Long id);

    List<Product> findAllWithEagerRelationships();

    String getProductById(int id);

    Iterable<Product> getAllProducts();

    void addProduct(Product product);*/



}
