package com.shop.onlineshop.urils.responses;

import com.shop.onlineshop.model.entities.Product;
import com.shop.onlineshop.model.entities.User;
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
