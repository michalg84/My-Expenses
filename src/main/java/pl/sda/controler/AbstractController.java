package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.service.BudgetService;
import pl.sda.service.CategoryService;
import pl.sda.service.TransactionService;
import pl.sda.service.user.UserService;
import pl.sda.service.webnotification.MessageService;

public abstract class AbstractController {
    @Autowired
    MessageService messageService;
    @Autowired
    TransactionService transactionService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    BudgetService budgetService;
    static final String USER_ACCOUNT = "user/account";
    static final String USER_TRANSACTIONS = "user/list";
    static final String BUDGET_LIST = "budget/list";

    /*
     * Appends "redrect:/" with list of string splited by '/'. Returns web address;
     */
    protected String buildRedirectPath(String[] strings) {
        StringBuilder builder = new StringBuilder("redirect:");
        for (String string : strings) {
            builder.append('/');
            builder.append(string);
        }
        System.out.println(builder.toString());
        return builder.toString();
    }
}
