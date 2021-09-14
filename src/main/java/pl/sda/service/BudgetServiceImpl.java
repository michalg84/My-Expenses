package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.BudgetDto;
import pl.sda.dto.MonthBudgetDto;
import pl.sda.model.Budget;
import pl.sda.model.Category;
import pl.sda.model.User;
import pl.sda.repository.BudgetRepository;
import pl.sda.repository.CategoryRepository;
import pl.sda.service.user.UserService;
import pl.sda.service.webnotification.MessageService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-23.
 */
@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetMapper budgetMapper = new BudgetMapper();
    private final CategoryMapper categoryMapper = new CategoryMapper();
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MessageService messageService;


    public List<BudgetDto> getBudgetDtoList(Integer year, Integer month) {

        User user = userService.getCurrentUser();
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
        List<Category> categories = categoryRepository.findByUser(userService.getCurrentUser());
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
        category.setUser(userService.getCurrentUser());
        Budget budget = budgetMapper.convertBudgetDtoToBudget(budgetDto);
        budget.setCategory(category);
        return budget;
    }
}
