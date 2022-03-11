package dev.galka.service;

import dev.galka.account.domain.User;
import dev.galka.dto.BudgetDto;
import dev.galka.dto.CategoryDto;
import dev.galka.dto.MonthBudgetDto;
import dev.galka.model.Budget;
import dev.galka.model.Category;
import dev.galka.model.CategoryMapper;
import dev.galka.repository.BudgetRepository;
import dev.galka.repository.CategoryRepository;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetMapper budgetMapper = new BudgetMapper();
    private final BudgetRepository budgetRepository;
    private final AuthUserProvider authUserProvider;
    private final CategoryRepository categoryRepository;
    private final MessageService messageService;
    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;


    public List<BudgetDto> getBudgetDtoList(Integer year, Integer month) {

        User user = authUserProvider.authenticatedUser();
        List<Budget> budgetList = budgetRepository.findAllBy(user, year, month);
        if (budgetList.isEmpty()) {
            return this.createBudgetsForAllCategories(fetchUserCategories());
        }
        List<BudgetDto> budgetDtoList = new ArrayList<>();
        for (Budget b : budgetList) {

            final BudgetDto budgetDto = budgetMapper.convertBudgetToBudgetDto(b);
            budgetDto.setCategoryDto(categoryMapper.map(b.getCategory()));

            budgetDtoList.add(budgetDto);
        }
        //todo: add used to BudgetDto from transactionList grouped by category
        return budgetDtoList;
    }

    private List<CategoryDto> fetchUserCategories() {
        final List<Category> categories = categoryRepository.findByUser(authUserProvider.authenticatedUser());
        return categories
                .stream()
                .map(categoryMapper::map)
                .collect(Collectors.toList());
    }

    private List<BudgetDto> createBudgetsForAllCategories(List<CategoryDto> categories) {
        return categories.stream()
                .map(createBudgetFor())
                .collect(Collectors.toList());
    }

    private Function<CategoryDto, BudgetDto> createBudgetFor() {
        return c -> {
            BudgetDto budgetDto = new BudgetDto();
            budgetDto.setCategoryDto(c);
            return budgetDto;
        };
    }

    public void add(MonthBudgetDto monthBudgetDto) {
        for (BudgetDto budgetDto : monthBudgetDto.getList()) {
            budgetDto.setDate(monthBudgetDto.getDate());
            Budget budget = convertToModel(budgetDto);
            try {
                budgetRepository.save(budget);
            } catch (Exception e) {
                messageService.addErrorMessage("Error adding budget to DB. \nDetails: " + budget);
                e.printStackTrace();
            }
        }
        messageService.addSuccessMessage("Budget added");
    }

    private Budget convertToModel(BudgetDto budgetDto) {
        Category category = categoryMapper.map(budgetDto.getCategoryDto());
        category.setUser(authUserProvider.authenticatedUser());
        Budget budget = budgetMapper.convertBudgetDtoToBudget(budgetDto);
        budget.setCategory(category);
        return budget;
    }
}
