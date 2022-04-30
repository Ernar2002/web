package com.shop.onlineshop.urils.responses;

import com.shop.onlineshop.model.entities.Category;
import com.shop.onlineshop.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductDto {
    private long productId;
    private String productName;
    private int productPrice;
    private String image;

    private String categoryName;

    public Product toProduct(){
        Product product = new Product();
        Category category = new Category();

        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setImage(image);
        category.setCategoryName(categoryName);
        product.setCategory(category);

        return product;
    }

    public static ProductDto fromProduct(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setImage(product.getImage());
        productDto.setCategoryName(product.getCategory().getCategoryName());

        return productDto;
    }
}
