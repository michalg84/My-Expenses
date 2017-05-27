package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.BudgetDto;
import pl.sda.dto.MonthBudget;
import pl.sda.model.Budget;
import pl.sda.model.Category;
import pl.sda.model.User;
import pl.sda.repository.BudgetRepository;
import pl.sda.repository.CategoryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-23.
 */
@Service
public class BudgetService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MessageService messageService;


    public List<BudgetDto> getBudgetDtoList() {
        User user = userService.getAcctualUser();
        List<Budget> budgetList = budgetRepository.findAllByUser(user);
        List<BudgetDto> budgetDtoList = new ArrayList<>();
        for (Budget b : budgetList) {
            budgetDtoList.add(convertBudgetToBudgetDto(b));
        }
        return budgetDtoList;
    }

    private BudgetDto convertBudgetToBudgetDto(Budget b) {
        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setCategory(b.getCategory());
        budgetDto.setMonth(b.getMonth());
        budgetDto.setYear(b.getYear());
        budgetDto.setSum(b.getSum());
        budgetDto.setUser(b.getUser());
        return budgetDto;
    }

    public List<BudgetDto> getNewBudgetDtos() {
        User user = userService.getAcctualUser();
        List<Category> categories = categoryRepository.findByUser(user);
        List<BudgetDto> budgetDtoList = new ArrayList<>();
        for (Category c : categories) {
            BudgetDto budgetDto = new BudgetDto();
            budgetDto.setCategory(c);
            budgetDtoList.add(budgetDto);
        }
        return budgetDtoList;
    }

    public void add(MonthBudget monthBudget) {

        messageService.addSuccessMessage("Budget added");
    }
}
