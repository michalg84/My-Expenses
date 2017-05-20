package pl.sda.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import pl.sda.model.Account;
import pl.sda.model.Role;
import pl.sda.model.Transaction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Created by Michał Gałka on 2017-04-07.
 */
public class UserDto {

    private Integer id;
    @NotEmpty(message = "Please insert username")
    @Size(min = 5, max = 25, message = "5 - 25 characters are requiered !")
    private String username;

    private String login;
    @NotEmpty
    @Email
    private String mail;
    @NotEmpty
    @Size(min = 5, max = 25, message = "5 - 25 characters are requiered !")
    private String password;
    @NotEmpty
    @Size(min = 5, max = 25, message = "5 - 25 characters are requiered !")
    private String confirmPassword;
    private List<Transaction> transactionList;
    private List<Account> accounts;
    private Set<Role> roles;

    public UserDto() {
    }

    public UserDto(Integer id, String username, String login, String mail, String password, String confirmPassword) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.mail = mail;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
