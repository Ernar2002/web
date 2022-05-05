package com.shop.onlineshop.repositories;

import com.shop.onlineshop.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_CategoryId(long CategoryId);

    @Query(value = "SELECT * FROM products WHERE product_name LIKE %:keyword%", nativeQuery = true)
    List<Product> getProductsByTitle(@Param("keyword") String title);



}
