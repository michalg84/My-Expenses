package pl.sda.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.BudgetDto;
import pl.sda.dto.MonthBudget;
import pl.sda.model.Budget;
import pl.sda.model.Category;
import pl.sda.model.User;
import pl.sda.repository.BudgetRepository;
import pl.sda.repository.CategoryRepository;
import pl.sda.service.user.UserService;

/**
 * Created by Michał Gałka on 2017-05-23.
 */
@Service
public class BudgetServiceImpl implements BudgetService {

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


    public List<BudgetDto> getBudgetDtoList(Integer year, Integer month) {

        User user = userService.getCurrentUser();
        List<Budget> budgetList = budgetRepository.findAllBy(user, year, month);
        if (budgetList.size() == 0) {
            return this.getNewBudgetDtos();
        }
        List<BudgetDto> budgetDtoList = new ArrayList<>();
        for (Budget b : budgetList) {
            budgetDtoList.add(convertBudgetToBudgetDto(b));
        }
        //todo: add used to BudgetDto from transactionList grouped by category
        return budgetDtoList;
    }


    public List<BudgetDto> getNewBudgetDtos() {
        User user = userService.getCurrentUser();
        List<Category> categories = categoryRepository.findByUser(user);
        List<BudgetDto> budgetDtoList = new ArrayList<>();
        for (Category c : categories) {
            BudgetDto budgetDto = new BudgetDto();
            budgetDto.setCategoryDto(categoryService.convertToDto(c));
            budgetDtoList.add(budgetDto);
        }
        return budgetDtoList;
    }

    public void add(MonthBudget monthBudget) {
        Date date = monthBudget.getDate();
//        List<Budget> budgets = new ArrayList<>();
        for (BudgetDto budgetDto : monthBudget.getList()) {
            budgetDto.setDate(date);
            Budget budget = this.convertBudgetDtoToBudget(budgetDto);
            try {
                budgetRepository.save(budget);
            } catch (Exception e) {
                messageService.addErrorMessage("Error adding budget to DB. \nDetails: " + budget.toString());
                e.printStackTrace();
            }
        }

        messageService.addSuccessMessage("Budget added");
    }


    private BudgetDto convertBudgetToBudgetDto(Budget b) {
        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(b.getId());
        budgetDto.setCategoryDto(categoryService.convertToDto(b.getCategory()));
        budgetDto.setDate(new Date(b.getYear(), b.getMonth(), 1));
        budgetDto.setSum(b.getSum());
        return budgetDto;
    }

    private Budget convertBudgetDtoToBudget(BudgetDto b) {
        Budget budget = new Budget();
        budget.setId(b.getId());
        budget.setCategory(categoryService.convertToModel(b.getCategoryDto()));
        budget.setSum(b.getSum());
        budget.setUser(userService.getCurrentUser());

        Calendar cal = Calendar.getInstance();
        cal.setTime(b.getDate());

        budget.setYear(cal.get(Calendar.YEAR));
        budget.setMonth(cal.get(Calendar.MONTH));
        return budget;
    }
}
