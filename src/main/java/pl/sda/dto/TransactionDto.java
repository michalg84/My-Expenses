package pl.sda.dto;

import pl.sda.model.Account;
import pl.sda.model.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Michał Gałka on 2017-04-17.
 */
public class TransactionDto {
    private Integer id;
    private User user;
    private BigDecimal amount;
    private Account fromAccount;
    private String toAccount;
    private Date transDate;
    private BigDecimal balance;

    public TransactionDto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
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
}
