package dev.galka.service;

import dev.galka.dto.BudgetDto;
import dev.galka.model.Budget;

import java.util.Calendar;
import java.util.Date;

public class BudgetMapper {

    BudgetDto convertBudgetToBudgetDto(Budget b) {
        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(b.getId());
        budgetDto.setDate(new Date(b.getYear(), b.getMonth(), 1));
        budgetDto.setSum(b.getSum());
        return budgetDto;
    }

    Budget convertBudgetDtoToBudget(BudgetDto b) {
        Budget budget = new Budget();
        budget.setId(b.getId());
        budget.setSum(b.getSum());

        Calendar cal = Calendar.getInstance();
        cal.setTime(b.getDate());

        budget.setYear(cal.get(Calendar.YEAR));
        budget.setMonth(cal.get(Calendar.MONTH));
        return budget;
    }
}