package dev.galka.service.user;

import dev.galka.account.domain.Role;
import dev.galka.account.inout.AccountDbEntity;
import dev.galka.model.Category;
import dev.galka.model.TransactionDbEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Integer id;
    @NotEmpty(message = "Please insert username")
    @Size(min = 5, max = 25, message = "5 - 25 characters are required !")
    private String username;

    private String login;
    @NotEmpty
    @Email
    private String mail;
    @NotEmpty
    @Size(min = 5, max = 25, message = "5 - 25 characters are required !")
    private String password;
    @NotEmpty
    @Size(min = 5, max = 25, message = "5 - 25 characters are required !")
    private String confirmPassword;
    private List<TransactionDbEntity> transactionList;
    private List<AccountDbEntity> accounts;
    private Set<Role> roles;
    private List<Category> categories;

}
