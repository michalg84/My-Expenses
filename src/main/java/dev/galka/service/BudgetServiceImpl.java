package dev.galka.service;

import dev.galka.account.domain.model.User;
import dev.galka.dto.BudgetDto;
import dev.galka.dto.MonthBudgetDto;
import dev.galka.model.Budget;
import dev.galka.model.Category;
import dev.galka.repository.BudgetRepository;
import dev.galka.repository.CategoryRepository;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetMapper budgetMapper = new BudgetMapper();
    private final CategoryMapper categoryMapper = new CategoryMapper();
    private final BudgetRepository budgetRepository;
    private final AuthUserProvider authUserProvider;
    private final CategoryRepository categoryRepository;
    private final MessageService messageService;


    public List<BudgetDto> getBudgetDtoList(Integer year, Integer month) {

        User user = authUserProvider.authenticatedUser();
        List<Budget> budgetList = budgetRepository.findAllBy(user, year, month);
        if (budgetList.isEmpty()) {
            return this.getNewBudgetDtos();
        }
        List<BudgetDto> budgetDtoList = new ArrayList<>();
        for (Budget b : budgetList) {

            final BudgetDto budgetDto = budgetMapper.convertBudgetToBudgetDto(b);
            budgetDto.setCategoryDto(categoryMapper.convertToDto(b.getCategory()));

            budgetDtoList.add(budgetDto);
        }
        //todo: add used to BudgetDto from transactionList grouped by category
        return budgetDtoList;
    }


    public List<BudgetDto> getNewBudgetDtos() {
        List<Category> categories = categoryRepository.findByUser(authUserProvider.authenticatedUser());
        List<BudgetDto> budgetDtoList = new ArrayList<>();
        for (Category c : categories) {
            BudgetDto budgetDto = new BudgetDto();
            budgetDto.setCategoryDto(categoryMapper.convertToDto(c));
            budgetDtoList.add(budgetDto);
        }
        return budgetDtoList;
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
        Category category = categoryMapper.convertToModel(budgetDto.getCategoryDto());
        category.setUser(authUserProvider.authenticatedUser());
        Budget budget = budgetMapper.convertBudgetDtoToBudget(budgetDto);
        budget.setCategory(category);
        return budget;
    }
}
