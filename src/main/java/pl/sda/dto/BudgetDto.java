package pl.sda.dto;

import pl.sda.model.Category;
import pl.sda.model.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Michał Gałka on 2017-05-23.
 */
public class BudgetDto {
    private Category category;
    private BigDecimal sum;
    private Date date;
    private Integer month;
    private Integer year;
    private User user;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
