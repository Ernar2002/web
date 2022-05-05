package com.shop.onlineshop.utils.responses;

import com.shop.onlineshop.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductResponse {
    private boolean error;
    private List<Product> product;
}
