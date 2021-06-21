package pl.sda.controler;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.AccountDto;
import pl.sda.dto.CategoryDto;
import pl.sda.dto.MoveCashDto;
import pl.sda.dto.TransactionDto;
import pl.sda.dto.UserDto;
import pl.sda.service.AccountService;
import pl.sda.service.AccountTypeService;

/**
 * Created by Michał Gałka on 2017-04-09.
 */
@Controller
@RequestMapping("user")
public class UserController extends AbstractController {
    @Autowired
    AccountTypeService accountTypeService;
    @Autowired
    AccountService accountService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("account")
    public ModelAndView userAccount(ModelMap modelMap, HttpSession session) {
        UserDto userDto = userService.getCurrentUserDto();
        session.setAttribute("username", userDto.getUsername());
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("accounts",
                accountService.getAccounts());
        modelMap.addAttribute("sum", userService.getTotalBalance());
        modelMap.addAttribute("newAccount", new AccountDto());
        modelMap.addAttribute("accountTypes", accountTypeService.getAccountTypes());
        return new ModelAndView(USER_ACCOUNT, modelMap);
    }


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

    @GetMapping("list")
    public ModelAndView transactionList(ModelMap modelMap) {
        UserDto userDto = userService.getCurrentUserDto();
        List<AccountDto> accounts = accountService.getUserAccounts();
        if (accounts.isEmpty()) {
            messageService.addWarnMessage(CREATE_ACCOUNT_FIRST);
        }
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("newCategory", new CategoryDto());
        modelMap.addAttribute("accounts", accounts);
        modelMap.addAttribute("categories", categoryService.getCategoriesList());
        List<TransactionDto> transactions = transactionService.getTransactionsWithBalance();
        modelMap.addAttribute("transactionList", transactions);
        modelMap.addAttribute("transactionDto", new TransactionDto());
        modelMap.addAttribute("moveCash", new MoveCashDto());

        return new ModelAndView(USER_TRANSACTIONS, modelMap);
    }

    //todo mail
    //todo logger
}
