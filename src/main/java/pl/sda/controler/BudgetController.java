package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.BudgetDto;
import pl.sda.dto.MonthBudget;
import pl.sda.service.BudgetService;
import pl.sda.service.UserService;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-23.
 */
@Controller
@RequestMapping("budget/")
public class BudgetController {
    @Autowired
    private UserService userService;

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/list")
    public ModelAndView budgetList(ModelMap modelMap) {
        MonthBudget monthBudget = new MonthBudget();
        List<BudgetDto> budgetList = null;
//                budgetService.getBudgetDtoList();
        if (budgetList == null || budgetList.size() == 0) {
            monthBudget.setList(budgetService.getNewBudgetDtos());
        }
        modelMap.addAttribute("monthBudget", monthBudget);
        return new ModelAndView("user/budget", modelMap);
    }

    @PostMapping("/add")
    public String addBudget(@ModelAttribute("monthBudget") MonthBudget monthBudget, ModelMap modelMap) {
        System.out.println(monthBudget);
        //todo: add month, year, save to DB. then add view list.
        budgetService.add(monthBudget);
        return null;
    }
}

