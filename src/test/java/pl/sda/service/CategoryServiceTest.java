package pl.sda.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.sda.model.Category;
import pl.sda.model.User;
import pl.sda.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by Michał Gałka on 2017-05-31.
 */
public class CategoryServiceTest {
    private MessageService messageService;
    private UserService userService;
    private CategoryRepository categoryRepository;
    private User user;
//    List<Category> list = createListOfCategories();

/*    @Before
    public void setUp() throws Exception {
        messageService = mock(MessageService.class);
        userService = mock(UserService.class);
        categoryRepository = mock(CategoryRepository.class);
//        user = userService.s
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void add() throws Exception {
        Category category = new Category("Category Service Test Example");
    }

    @Test
    public void initialCategories() throws Exception {
    }

    @Test
    public void getCategories() throws Exception {
        createListOfCategories();



    }

    private List<Category> createListOfCategories() {
        List<Category> list = new ArrayList<>();
        Category category1 = new Category("FOOD", user);
        Category category2 = new Category("CAR", user);
        Category category3 = new Category("GIFTS", user);
        Category category4 = new Category("BILLS", user);
        Category category5 = new Category("FURNITURE", user);
        list.add(category1);
        list.add(category2);
        list.add(category3);
        list.add(category4);
        list.add(category5);
        return list;
    }*/

}