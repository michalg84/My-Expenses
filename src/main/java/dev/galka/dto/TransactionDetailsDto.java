package dev.galka.dto;

import dev.galka.validators.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
public class TransactionDetailsDto {
    private Integer id;
    @Currency(message = "Invalid amount entry. Please insert correct currency value.")
    private BigDecimal amount;
    private String accountName;
    @Valid
    private String comment;
    @Valid
    private Date transDate;
    @Setter
    private BigDecimal balance;
    private CategoryDto category; //TODO use CategoryDto

    @Override
    public String toString() {
        return "TransactionDto{" +
               "id=" + id +
               ", amount=" + amount +
               ", account=" + accountName +
               ", comment='" + comment + '\'' +
               ", transDate=" + transDate +
               ", balance=" + balance +
               ", category=" + category +
               '}';
    }
}
