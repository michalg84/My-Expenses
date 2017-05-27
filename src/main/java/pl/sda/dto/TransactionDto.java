package pl.sda.dto;

import org.springframework.format.annotation.NumberFormat;
import pl.sda.model.Account;
import pl.sda.model.Category;
import pl.sda.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Michał Gałka on 2017-04-17.
 */
public class TransactionDto {
    private Integer id;
//    @Pattern(regexp = "[0-9].[0-9]")
    @NumberFormat(style = NumberFormat.Style.DEFAULT)
    private BigDecimal amount;
    private Account account;
    @Valid
    private String comment;
    @Valid
    private Date transDate;
    private BigDecimal balance;
    private Category category;

    public TransactionDto() {

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
