package com.shop.onlineshop.controller.api;

import com.shop.onlineshop.model.entities.Category;
import com.shop.onlineshop.model.entities.Product;
import com.shop.onlineshop.service.CategoryService;
import com.shop.onlineshop.service.ProductService;
import com.shop.onlineshop.service.UserService;
import com.shop.onlineshop.utils.CategoryDto;
import com.shop.onlineshop.utils.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/get-categories")
    public List<CategoryDto> getCategories(){
        List<Category> categories = categoryService.listCategory();
        List<CategoryDto> categoryDtoList = new ArrayList<>();

        for(Category category: categories){
            categoryDtoList.add(CategoryDto.fromCategory(category));
        }

        return categoryDtoList;
    }

    @GetMapping("/get-products")
    public List<ProductDto> getProducts() {
        List<Product> productList = productService.listProduct();
        List<ProductDto> productDtoList = new ArrayList<>();

        for(Product product: productList){
            productDtoList.add(ProductDto.fromProduct(product));
        }

        return productDtoList;
    }

    @GetMapping("/search-products")
    public List<ProductDto> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.findByName(keyword);

        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : products) {
            productDtoList.add(ProductDto.fromProduct(product));
        }
        return productDtoList;
    }

    @GetMapping("/get-products/{categoryId}")
    public List<ProductDto> getProductFromCategory(@PathVariable("categoryId") Long categoryId) {
        List<Product> productList = productService.findByCategory(categoryId);
        List<ProductDto> productDtoList = new ArrayList<>();

        for(Product product: productList){
            productDtoList.add(ProductDto.fromProduct(product));
        }

        return productDtoList;
    }

}
