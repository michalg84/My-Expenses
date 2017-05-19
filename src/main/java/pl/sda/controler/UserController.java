package pl.sda.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.AccountDto;
import pl.sda.dto.TransactionDto;
import pl.sda.dto.UserDto;
import pl.sda.model.Account;
import pl.sda.model.User;
import pl.sda.service.AccountService;
import pl.sda.service.AccountTypeService;
import pl.sda.service.TransactionService;
import pl.sda.service.UserService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-04-09.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    public static final String USER_ACCOUNT_ADD = "user/account/add";
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

    private UserDto acctualUserDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
            return userService.findUserDtoByUsername(authentication.getName());
        }
        return null;
    }
    private User acctualUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
            return userService.findUserByUsername(authentication.getName());
        }
        return null;
    }


    @RequestMapping("account")
    public ModelAndView userAccount(ModelMap modelMap) {
        modelMap.addAttribute("userDto", acctualUserDto());
        modelMap.addAttribute("accounts", accountService.getAccounts(acctualUser()));
        modelMap.addAttribute("sum", userService.getTotalBalance(acctualUser()));
        return new ModelAndView(USER_ACCOUNT, modelMap);
    }

    @GetMapping("account/add")
    public ModelAndView addAccount(ModelMap modelMap) {
        modelMap.addAttribute("newAccount", new AccountDto());
        modelMap.addAttribute("accountTypes", accountTypeService.getAccountTypes());
        return new ModelAndView(USER_ACCOUNT_ADD, modelMap);
    }

    @GetMapping("/list")
    public ModelAndView transactionList(ModelMap modelMap) {
        List<TransactionDto> transactionDtoList = transactionService.getByUserId(acctualUserDto().getId());
        modelMap.addAttribute("userDto", acctualUserDto());
        modelMap.addAttribute("transactionDtoList", transactionDtoList);
        //TODO: zbudować strone i przekazać do niej wyniki.
        return new ModelAndView(USER_TRANSACTIONS, modelMap);
    }
}
