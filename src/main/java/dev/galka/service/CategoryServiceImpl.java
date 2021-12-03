package dev.galka.service;

import dev.galka.dto.CategoryDto;
import dev.galka.model.Category;
import dev.galka.model.User;
import dev.galka.repository.CategoryRepository;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper = new CategoryMapper();
    private final MessageService messageService;
    private final AuthUserProvider authUserProvider;
    private final CategoryRepository categoryRepository;

    public void add(CategoryDto categoryDto) {
        User user = authUserProvider.authenticatedUser();
        Category category = new Category();
        category.setUser(user);
        category.setName(categoryDto.getName().toUpperCase());
        Integer exists = categoryRepository.ifExists(category.getName().toUpperCase(), user);
        if (exists > 0)
            messageService.addErrorMessage(String.format("Category %s already exists", category.getName()));
        else {
            categoryRepository.save(category);
            messageService.addSuccessMessage(String.format("Category %s successfuly added", category.getName()));
        }
    }


    public List<Category> initialCategories(User user) {
        List<Category> categories = new ArrayList<>();

        categories.add(new Category("MOVE BETWEEN ACCOUNTS"));
        categories.add(new Category("FOOD"));
        categories.add(new Category("CAR"));
        categories.add(new Category("ENTERTAIMENT"));
        categories.add(new Category("SPORT"));
        categories.add(new Category("GIFTS"));
        categories.add(new Category("RENT"));
        categories.add(new Category("TAXES"));
        categories.add(new Category("BILLS"));
        categories.add(new Category("SALARY"));
        categories.forEach(c -> c.setUser(user));
        return sort(categories);
    }

    private List<Category> sort(List<Category> categories) {
        return categories.stream()
                .sorted(Comparator.comparing(Category::getName))
                .collect(Collectors.toList());
    }


    public List<Category> getCategoriesList() {
        List<Category> list = categoryRepository.findAllByUser(authUserProvider.authenticatedUser());
        list = list.stream()
                .filter(category -> !category.getName().equals("MOVE BETWEEN ACCOUNTS"))
                .collect(Collectors.toList());
        return this.sort(list);
    }

    @Override
    public CategoryDto convertToDto(Category category) {
        return categoryMapper.convertToDto(category);
    }

    @Override
    public Category convertToModel(CategoryDto categoryDto) {
        final Category category = categoryMapper.convertToModel(categoryDto);
        category.setUser(authUserProvider.authenticatedUser());
        return category;
    }

    @Override
    public void save(List<Category> categories) {
        categoryRepository.saveAll(categories);
    }


}
