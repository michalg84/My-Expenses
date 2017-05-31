package pl.sda.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sda.dto.UserDto;
import pl.sda.model.Role;
import pl.sda.model.User;
import pl.sda.repository.AccountRepository;
import pl.sda.repository.CategoryRepository;
import pl.sda.repository.RoleRepository;
import pl.sda.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Michał Gałka on 2017-05-31.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService = new UserService();
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private CategoryService categoryService;
    @Mock
    private MessageService messageService;


    private User testUser;
    private UserDto testUserDto;

    @Before
    public void setUp() throws Exception {


        Set<Role> roles = new HashSet<Role>();
        when(roleRepository.findOne(1)).thenReturn(new Role(1, "testRole1", null));
        when(roleRepository.findOne(2)).thenReturn(new Role(2, "testRole2", null));
        roles.add(roleRepository.findOne(1));
        roles.add(roleRepository.findOne(2));

        testUser = new User();
        testUser.setPassword("xxx##$$55");
        testUser.setLogin("testUser");
        testUser.setMail("testUser@mail.com");
        testUser.setUsername("testUsername");
        testUser.setRoles(roles);
        testUser.setId(null);

        testUserDto = new UserDto();
        testUserDto.setPassword("xxx##$$55");
        testUserDto.setLogin("testUser");
        testUserDto.setMail("testUser@mail.com");
        testUserDto.setUsername("testUsername");
        testUserDto.setRoles(roles);
        testUserDto.setId(null);


    }

    @Ignore
    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void save() throws Exception {
    }

    @Test
    public void getAcctualUserDto() throws Exception {
//        userService = mock(UserService.class);
        when(userService.getAcctualUser()).thenReturn(null);
        assertNull(userService.getAcctualUserDto());
        //TODO test when user is not null
//        User user = testUser;
//        when(userService.getAcctualUser()).thenReturn(user);
//        UserDto userDto = userService.getAcctualUserDto();
//        assertEquals(userDto, testUserDto);

    }

    @Ignore
    @Test
    public void getAcctualUser() throws Exception {
    }

    @Test
    public void convertUserDtoToUser() throws Exception {
        User checkUser = testUser;
        User convertedUserDto = userService.convertUserDtoToUser(testUserDto);
        assertEquals(checkUser.getId(), convertedUserDto.getId());
        assertEquals(checkUser.getLogin(), convertedUserDto.getLogin());
        assertEquals(checkUser.getMail(), convertedUserDto.getMail());
        assertEquals(checkUser.getRoles(), convertedUserDto.getRoles());
        assertEquals(checkUser.getUsername(), convertedUserDto.getUsername());
        assertEquals(checkUser.getPassword(), convertedUserDto.getPassword());

    }

    @Test
    public void convertUserToUserDto() throws Exception {
        UserDto checkUserDto = testUserDto;
        UserDto convertedUser = userService.convertUserToUserDto(testUser);
        assertEquals(checkUserDto.getId(), convertedUser.getId());
        assertEquals(checkUserDto.getLogin(), convertedUser.getLogin());
        assertEquals(checkUserDto.getMail(), convertedUser.getMail());
        assertEquals(checkUserDto.getRoles(), convertedUser.getRoles());
        assertEquals(checkUserDto.getUsername(), convertedUser.getUsername());
        assertNull(convertedUser.getPassword());
    }


    @Ignore
    @Test
    public void getAll() throws Exception {
    }

    @Ignore
    @Test
    public void findUserDtoByUsername() throws Exception {
    }

    @Ignore
    @Test
    public void getTotalBalance() throws Exception {
    }

    @Ignore
    @Test
    public void findUserByUsername() throws Exception {
    }

    @Ignore
    @Test
    public void getAccounts() throws Exception {
    }

}