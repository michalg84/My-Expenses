package pl.sda.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.AccountDto;
import pl.sda.dto.MessageDto;
import pl.sda.dto.TransactionDto;
import pl.sda.dto.UserDto;
import pl.sda.model.Category;
import pl.sda.model.Transaction;
import pl.sda.service.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-04-09.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    public static final String USER_ACCOUNT = "user/account";
    public static final String USER_TRANSACTIONS = "user/list";
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AccountTypeService accountTypeService;


    @RequestMapping("account")
    public ModelAndView userAccount(ModelMap modelMap, HttpSession session) {
        UserDto userDto = userService.getAcctualUserDto();
        session.setAttribute("username", userDto.getUsername());
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("accounts", userDto.getAccounts());
        modelMap.addAttribute("sum", userService.getTotalBalance());
        modelMap.addAttribute("newAccount", new AccountDto());
        modelMap.addAttribute("accountTypes", accountTypeService.getAccountTypes());
        return new ModelAndView(USER_ACCOUNT, modelMap);
    }

    /**
     * Adds new money account to database.
     *
     * @param accountDto
     * @param result
     * @param modelMap
     * @return
     */
    @PostMapping("account/add")
    public String addAccount(@ModelAttribute(name = "newAccount") @Valid AccountDto accountDto,
                             BindingResult result, ModelMap modelMap) {
        if (!result.hasErrors()) {
            accountService.addAccount(accountDto);
            return "redirect:/" + USER_ACCOUNT;
        }
//        String txt = result.getFieldError().getField()
        messageService.addErrorMessage("Error. Cannot add new account !" + result.getAllErrors());
        return "redirect:/" + USER_ACCOUNT;
    }

    @GetMapping("/list")
    public ModelAndView transactionList(ModelMap modelMap) {
        UserDto userDto = userService.getAcctualUserDto();
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("newCategory", new Category());
        List<TransactionDto> transactions = transactionService.getTransactionsWithBalance(userDto.getTransactionList());
        modelMap.addAttribute("transactionList", transactions);
        modelMap.addAttribute("transactionDto", new TransactionDto());
        return new ModelAndView(USER_TRANSACTIONS, modelMap);
    }

    @PostMapping("addTransaction")
    public String addNewTransaction(@ModelAttribute("transactionDto") @Valid TransactionDto transactionDto,
                                    BindingResult result, ModelMap modelMap) {
        if (!result.hasErrors()) {
            transactionService.addTransaction(transactionDto);
            return "redirect:/" + USER_TRANSACTIONS;
        }
        messageService.addErrorMessage("Transaction Error. Values aren't correct. Please try again.");
//        transactionList(modelMap, new MessageDto("Bad value! = " + transactionDto.getAmount()));
        return "redirect:/" + USER_TRANSACTIONS;
    }

    @PostMapping("removeTransaction/{id}")
    public String removeTransaction(@PathVariable("id") Integer transId) {
        if (transId != null) {
            transactionService.removeById(transId);
        }
        return "redirect:/" + USER_TRANSACTIONS;
    }

    @PostMapping("addCategory")
    public String addNewCategory(@ModelAttribute("newCategory") Category category) {
       categoryService.add(category);
        return "redirect:/" + USER_TRANSACTIONS;
    }

    //todo mail
    //todo logger
}
