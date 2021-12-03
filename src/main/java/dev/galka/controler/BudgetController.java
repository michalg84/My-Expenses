package dev.galka.controler;

import dev.galka.dto.MonthBudgetDto;
import dev.galka.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;

import static java.lang.String.valueOf;

@Controller
@RequestMapping("budget")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @GetMapping("list")
    public String viewBudget() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        return buildRedirectPath("budget", "list", valueOf(year), valueOf(month));
    }

    @GetMapping("list/{year}/{month}")
    public ModelAndView budgetList(@PathVariable(required = false) Integer year,
                                   @PathVariable(required = false) Integer month,
                                   ModelMap modelMap) {

        MonthBudgetDto newBudget = new MonthBudgetDto();
        newBudget.setList(budgetService.getBudgetDtoList(year, month));

        modelMap.addAttribute("monthBudget", newBudget);
        return new ModelAndView("user/budget", modelMap);
    }

    @PostMapping("add")
    public String addBudget(@ModelAttribute("monthBudget") MonthBudgetDto monthBudgetDto, ModelMap modelMap) {
        budgetService.add(monthBudgetDto);
        return buildRedirectPath("budget", "list");
    }

    protected String buildRedirectPath(String... strings) {
        StringBuilder builder = new StringBuilder("redirect:");
        for (String string : strings) {
            builder.append('/');
            builder.append(string);
        }
        return builder.toString();
    }
}
