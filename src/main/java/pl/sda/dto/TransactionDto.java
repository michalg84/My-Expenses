package pl.sda.dto;

import pl.sda.model.Account;
import pl.sda.model.Category;
import pl.sda.validators.Currency;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Michał Gałka on 2017-04-17.
 */
public class TransactionDto {
    private Integer id;
    @Currency(message = "Invalid amount entry. Please insert correct currency value.")
    private BigDecimal amount;
    private Account account; //TODO use Account DTO or ID
    //TODO use separate Dto for each operaton SaveTransDto, ShowtransDto, etc...
    @Valid
    private String comment;
    @Valid
    private Date transDate;
    private BigDecimal balance;
    private Category category; //TODO use CategoryDto

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", amount=" + amount +
                ", account=" + account +
                ", comment='" + comment + '\'' +
                ", transDate=" + transDate +
                ", balance=" + balance +
                ", category=" + category +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
