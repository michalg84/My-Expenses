package pl.sda.service.user;

import java.util.List;
import java.util.Set;

import pl.sda.model.Account;
import pl.sda.model.Category;
import pl.sda.model.Role;
import pl.sda.model.Transaction;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    private List<Transaction> transactionList;
    private List<Account> accounts;
    private Set<Role> roles;
    private List<Category> categories;

    public UserDto() {
    }

    public UserDto(Integer id,
                   String username,
                   String login,
                   String mail,
                   String password,
                   String confirmPassword,
                   List<Category> categories) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.mail = mail;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.categories = categories;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
