package pl.sda.dto;

import org.springframework.format.annotation.NumberFormat;
import pl.sda.model.AccountType;
import pl.sda.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Michał Gałka on 2017-05-18.
 */
public class AccountDto {

    private Integer id;
    private Date creationDate;
    @NotNull(message = "Please insert name")
    private String name;
    private Integer accountNumber;
    @NotNull(message = "Please choose account type")
    private AccountType accountType;
    private User user;
    @NotNull(message = "Please insert ballance")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal balance;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

