package dev.galka.dto;

import java.util.Date;
import java.util.List;

public class MonthBudgetDto {
    private List<BudgetDto> list;
    private Date date;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<BudgetDto> getList() {
        return list;
    }

    public void setList(List<BudgetDto> list) {
        this.list = list;
    }
}
