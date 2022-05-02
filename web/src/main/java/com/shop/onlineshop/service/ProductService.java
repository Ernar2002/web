package com.shop.onlineshop.service;

import com.shop.onlineshop.model.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void addProduct(Product product);

    List<Product> listProduct();

    Optional<Product> getProductById(long productId);

    List<Product> findByCategory(long categoryId);

    List<Product> findByName(String title);

    void deleteProduct(Long productId);



}
