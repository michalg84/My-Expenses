package pl.sda.service;

import pl.sda.dto.BudgetDto;
import pl.sda.dto.MonthBudgetDto;

import java.util.List;

public interface BudgetService {

    List<BudgetDto> getBudgetDtoList(Integer year, Integer month);

    List<BudgetDto> getNewBudgetDtos();

    void add(MonthBudgetDto monthBudgetDto);

}
