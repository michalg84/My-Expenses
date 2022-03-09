package dev.galka.service;

import dev.galka.account.domain.User;
import dev.galka.dto.CategoryDto;
import dev.galka.model.Category;

import java.util.List;

public interface CategoryService {

    void add(CategoryDto categoryDto);

    List<Category> initialCategories(User user);

    List<Category> getCategoriesList();

    void save(List<Category> categories);
}
