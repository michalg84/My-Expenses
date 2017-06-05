package pl.sda.service;

import pl.sda.dto.BudgetDto;
import pl.sda.dto.MonthBudget;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-23.
 */
public interface BudgetService {

    List<BudgetDto> getBudgetDtoList(Integer year, Integer month);

    List<BudgetDto> getNewBudgetDtos();

    void add(MonthBudget monthBudget);

}
