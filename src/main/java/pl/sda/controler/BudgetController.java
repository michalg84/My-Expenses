package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.MonthBudget;
import pl.sda.service.BudgetService;
import pl.sda.service.UserService;

import java.util.Calendar;
import java.util.Date;

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

    @GetMapping("list")
    public String viewBudget() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        Integer month = cal.get(Calendar.MONTH);
        return "redirect:/budget/list/" + year + "/" + month;
    }

    @GetMapping("/list/{year}/{month}")
    public ModelAndView budgetList(@PathVariable(required = false) Integer year,
                                   @PathVariable(required = false) Integer month,
                                   ModelMap modelMap) {

        MonthBudget newMonthBudget = new MonthBudget();
        newMonthBudget.setList(budgetService.getBudgetDtoList(year, month));

        modelMap.addAttribute("monthBudget", newMonthBudget);
        return new ModelAndView("user/budget", modelMap);
    }

    @PostMapping("/add")
    public String addBudget(@ModelAttribute("monthBudget") MonthBudget monthBudget, ModelMap modelMap) {
        System.out.println(monthBudget);
        budgetService.add(monthBudget);
        return "redirect:/budget/list";
    }
}

