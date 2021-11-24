package pl.sda.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BudgetDto {
    private Integer id;
    private CategoryDto categoryDto;
    private BigDecimal sum;
    private BigDecimal used;
    private Date date;
    private BigDecimal usage = BigDecimal.ZERO;


    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public BigDecimal getUsed() {
        return used;
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getUsage() {
        return usage;
    }

    public void setUsage(BigDecimal usage) {
        this.usage = usage;
    }
}
