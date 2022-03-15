package dev.galka.account.dto;

import dev.galka.account.domain.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class AccountDto {

    private Integer id;
    private Date creationDate;
    @NotNull(message = "Please insert name")
    private String name;
    @NotNull
    private String accountNumber;
    @NotNull(message = "Please choose account type")
    private AccountType accountType;
    @NotNull(message = "Please insert ballance")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal balance;

    public AccountIdNameDtoView createIdNameDto() {
        return new AccountIdNameDtoView(id, name);
    }

}

