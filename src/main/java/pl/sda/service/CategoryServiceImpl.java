package pl.sda.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.CategoryDto;
import pl.sda.model.Category;
import pl.sda.model.User;
import pl.sda.repository.CategoryRepository;
import pl.sda.service.user.UserService;
import pl.sda.service.webnotification.MessageService;

/**
 * Created by Michał Gałka on 2017-05-22.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;

    public void add(CategoryDto categoryDto) {
        User user = userService.getCurrentUser();
        Category category = new Category();
        category.setUser(user);
        category.setName(categoryDto.getName().toUpperCase());
        Integer exists = categoryRepository.ifExists(category.getName().toUpperCase(), user);
        if (exists > 0)
            messageService.addErrorMessage(String.format("Category %s already exists", category.getName()));
        else {
            messageService.addSuccessMessage(String.format("Category %s successfuly added", category.getName()));
            categoryRepository.save(category);
        }
    }

    /**
     * Creates base group of categories.
     *
     * @return List of User's categories.
     */
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

    /**
     * Sort list of categories by name.
     *
     * @param categories to be sorted.
     * @return sorted categories.
     */
    private List<Category> sort(List<Category> categories) {
        return categories.stream()
                .sorted(Comparator.comparing(Category::getName))
                .collect(Collectors.toList());
    }


    public List<Category> getCategoriesList() {
        List<Category> list = categoryRepository.findAll(userService.getCurrentUser());
        list = list.stream()
                .filter(category -> !category.getName().equals("MOVE BETWEEN ACCOUNTS"))
                .collect(Collectors.toList());
        return this.sort(list);
    }

    @Override
    public CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    @Override
    public Category convertToModel(CategoryDto categoryDto) {
        Category category = new Category();
        category.setUser(userService.getCurrentUser());
        category.setName(categoryDto.getName());
        category.setId(categoryDto.getId());
        return category;
    }

    @Override
    public void save(List<Category> categories) {
        categoryRepository.save(categories);
    }


}
