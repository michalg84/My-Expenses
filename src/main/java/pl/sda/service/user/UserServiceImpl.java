package pl.sda.service.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import pl.sda.model.Category;
import pl.sda.model.Role;
import pl.sda.model.User;
import pl.sda.service.CategoryService;
import pl.sda.service.account.AccountService;
import pl.sda.service.crypto.PasswordService;
import pl.sda.service.webnotification.MessageService;

/**
 * Created by Michał Gałka on 2017-04-07.
 */
@Service
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    /**
     * Converts authenticated User to UserDto.
     *
     * @return UserDto of current logged User.
     */
    public UserDto getCurrentUserDto() {
        if (this.getCurrentUser() == null)
            return null;
        return UserMapper.map(this.getCurrentUser());
    }

    public Integer getCurrentUserId() {
        if (this.getCurrentUser() == null)
            return null;
        return this.getCurrentUser().getId();
    }

    /**
     * Gets authenticated User username and gets his User object form DB.
     *
     * @return Authenticated User object.
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
            return findUserByUsername(authentication.getName());
        }
        return null;
    }

    /**
     * Adds UserDto to Database.
     *
     * @param userDto User to be saved to database.
     */
    public void save(UserDto userDto) {
        User user = UserMapper.map(userDto);
        user.setPassword(passwordService.encode(userDto.getPassword()));
        user.setRoles(new HashSet<>());


        Role userRole = roleService.get(1);
        user.getRoles().add(userRole);

        List<Category> categories = categoryService.initialCategories(user);
        categoryService.save(categories);

        userRepository.save(user);
    }


    /**
     * Gets All UsersDto.
     *
     * @return List of all UserDto.
     */
    public List<UserDto> getAll() {
        List<UserDto> usersDto = new ArrayList<>();

        try {
            List<User> users = userRepository.findAll();
            for (User u : users) {
                UserDto userDto = new UserDto();
                userDto.setUsername(u.getUsername());
                usersDto.add(userDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersDto;
    }

    /**
     * Finds UserDto using username
     *
     * @param username Authenticated Users username.
     * @return UserDto.
     */
    public UserDto findUserDtoByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return UserMapper.map(user);
    }

    /**
     * Gets acctual total acount balance for acctual user.
     *
     * @return Users account balance from all Accounts.
     */
    public BigDecimal getTotalBalance() {
        return accountService.getTotalBalance();
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public FieldError checkIfSuchUserExists(UserDto userDto) {
        FieldError fieldError;
        if (userRepository.findByUsername(userDto.getUsername()) != null)
            return fieldError = new FieldError("userDto", "username", "Such user already exists");
        if (userRepository.findUserByMail(userDto.getMail()) != null)
            return fieldError = new FieldError("userDto", "mail", "Such e-mail already exists.");
        return null;
    }
}
