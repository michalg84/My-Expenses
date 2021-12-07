package dev.galka.service;

import dev.galka.account.domain.model.User;
import dev.galka.dto.CategoryDto;
import dev.galka.model.Category;

import java.util.List;

public interface CategoryService {

    void add(CategoryDto categoryDto);

    List<Category> initialCategories(User user);

    List<Category> getCategoriesList();

    CategoryDto convertToDto(Category category);

    Category convertToModel(CategoryDto categoryDto);

    void save(List<Category> categories);
}
