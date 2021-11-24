package pl.sda.service;

import java.util.List;
import pl.sda.dto.CategoryDto;
import pl.sda.model.Category;
import pl.sda.model.User;

public interface CategoryService {

    void add(CategoryDto categoryDto);

    List<Category> initialCategories(User user);

    List<Category> getCategoriesList();

    CategoryDto convertToDto(Category category);

    Category convertToModel(CategoryDto categoryDto);

    void save(List<Category> categories);
}
