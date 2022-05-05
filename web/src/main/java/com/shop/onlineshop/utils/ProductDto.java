package com.shop.onlineshop.urils;

import com.shop.onlineshop.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductDto {
    private long productId;
    private String productName;
    private int productPrice;
    private String image;

    private String categoryName;

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
