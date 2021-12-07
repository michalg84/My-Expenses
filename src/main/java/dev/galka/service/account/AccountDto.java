package dev.galka.service.account;

import dev.galka.account.domain.model.AccountType;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class AccountDto {

    private Integer id;
    private Date creationDate;
    @NotNull(message = "Please insert name")
    private String name;
//    @Pattern(regexp = "[a-zA-Z0-9*]", message = "For account number numbers, letters and '*' are allowed")
    //TODO: change for String
    private Integer accountNumber;
    @NotNull(message = "Please choose account type")
    private AccountType accountType;
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



    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

