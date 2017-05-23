package pl.sda.dto;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-23.
 */
public class MonthBudget {
    private List<BudgetDto> list;


    public List<BudgetDto> getList() {
        return list;
    }

    public void setList(List<BudgetDto> list) {
        this.list = list;
    }
}
