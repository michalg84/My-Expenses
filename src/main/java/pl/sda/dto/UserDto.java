package pl.sda.dto;

/**
 * Created by Michał Gałka on 2017-04-07.
 */
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String login;
    private String mail;
    private String password;
    private String confirmPassword;

    public UserDto() {
    }

    public UserDto(Integer id, String firstName, String lastName, String login, String mail, String password, String confirmPassword) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
