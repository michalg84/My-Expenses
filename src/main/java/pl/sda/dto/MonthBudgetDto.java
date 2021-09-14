package pl.sda.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-23.
 */
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
