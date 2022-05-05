package com.shop.onlineshop.utils;

import com.shop.onlineshop.model.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String name;

    public static CategoryDto fromCategory(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setName(category.getCategoryName());

        return categoryDto;
    }
}
