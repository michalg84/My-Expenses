package dev.galka.dto;

import dev.galka.account.adapters.out.AccountDbEntity;
import dev.galka.model.Category;
import dev.galka.validators.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class TransactionDto {
    private Integer id;
    @Currency(message = "Invalid amount entry. Please insert correct currency value.")
    private BigDecimal amount;
    private AccountDbEntity account; //TODO use Account DTO or ID
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
}
