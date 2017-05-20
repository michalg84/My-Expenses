package pl.sda.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.AccountDto;
import pl.sda.dto.TransactionDto;
import pl.sda.dto.UserDto;
import pl.sda.model.Account;
import pl.sda.model.Transaction;
import pl.sda.model.User;
import pl.sda.service.AccountService;
import pl.sda.service.AccountTypeService;
import pl.sda.service.TransactionService;
import pl.sda.service.UserService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-04-09.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    public static final String USER_ACCOUNT_ADD = "user/AddAccount";
    public static final String USER_ACCOUNT = "user/account";
    public static final String USER_TRANSACTIONS = "user/list";
    private final Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountTypeService accountTypeService;




    @RequestMapping("account")
    public ModelAndView userAccount(ModelMap modelMap) {
        UserDto userDto = userService.getAcctualUserDto();
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("accounts", userDto.getAccounts());
        modelMap.addAttribute("sum", userService.getTotalBalance());
        modelMap.addAttribute("newAccount", new AccountDto());
        modelMap.addAttribute("accountTypes", accountTypeService.getAccountTypes());
        return new ModelAndView(USER_ACCOUNT, modelMap);
    }

//    @GetMapping("account/add")
//    public ModelAndView addAccountPage(ModelMap modelMap) {
//        modelMap.addAttribute("newAccount", new AccountDto());
//        modelMap.addAttribute("accountTypes", accountTypeService.getAccountTypes());
//        return new ModelAndView(USER_ACCOUNT_ADD, modelMap);
//    }
    @PostMapping("account/add")
    public String addAccount(@ModelAttribute(name = "newAccount") @Valid AccountDto accountDto,
                             BindingResult result, ModelMap modelMap) {
        if (!result.hasErrors()) {
            accountService.addAccount(accountDto);
            return "redirect:/" + USER_ACCOUNT;
        }
        return USER_ACCOUNT_ADD;
    }

    @GetMapping("/list")
    public ModelAndView transactionList(ModelMap modelMap) {
        UserDto userDto = userService.getAcctualUserDto();
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("transactionList", userDto.getTransactionList());
        BigDecimal sum = transactionService.getTransactionSum(userDto.getTransactionList());
        modelMap.addAttribute("sum", sum);
        return new ModelAndView(USER_TRANSACTIONS, modelMap);
    }
}
