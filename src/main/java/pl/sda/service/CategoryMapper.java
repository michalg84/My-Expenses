package pl.sda.service;

import pl.sda.dto.CategoryDto;
import pl.sda.model.Category;

public class CategoryMapper {


    public CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category convertToModel(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setId(categoryDto.getId());
        return category;
    }
}