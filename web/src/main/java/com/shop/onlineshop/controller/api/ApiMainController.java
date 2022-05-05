package com.shop.onlineshop.controller.api;

import com.shop.onlineshop.model.entities.Product;
import com.shop.onlineshop.service.CategoryService;
import com.shop.onlineshop.service.ProductService;
import com.shop.onlineshop.service.UserService;
import com.shop.onlineshop.utils.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiMainController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/get-products")
    public List<ProductDto> getProducts() {
        List<Product> productList = productService.listProduct();
        List<ProductDto> productDtoList = new ArrayList<>();

        for(Product product: productList){
            productDtoList.add(ProductDto.fromProduct(product));
        }

        return productDtoList;
    }


}
