package pl.sda.service;

import pl.sda.dto.CategoryDto;
import pl.sda.model.Category;
import pl.sda.model.User;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-22.
 */
public interface CategoryService {

    void add(CategoryDto categoryDto);

    List<Category> initialCategories(User user);

    List<Category> getCategoriesList();

    CategoryDto convertToDto(Category category);

    Category convertToModel(CategoryDto categoryDto);
}
