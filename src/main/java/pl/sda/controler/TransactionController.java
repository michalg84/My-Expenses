package pl.sda.controler;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.CategoryDto;
import pl.sda.dto.MoveCashDto;
import pl.sda.dto.TransactionDto;
import pl.sda.service.CategoryService;
import pl.sda.service.TransactionService;
import pl.sda.service.account.AccountDto;
import pl.sda.service.account.AccountService;
import pl.sda.service.user.UserDto;
import pl.sda.service.user.UserService;
import pl.sda.service.webnotification.MessageService;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-04-09.
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private static final String CREATE_ACCOUNT_FIRST = "You need to create cash account before making transactions.";
    private static final String USER_TRANSACTIONS = "transaction/list";
    private static final String USER_TRANSACTION_LIST_VIEW = "user/list";
    private final Logger log = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    private String REDIRECT = "redirect:/";

    @GetMapping("list")
    public ModelAndView transactionList(ModelMap modelMap) {
        UserDto userDto = userService.getCurrentUserDto();
        List<AccountDto> accounts = accountService.getAccounts(userService.getCurrentUserId());
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

        return new ModelAndView(USER_TRANSACTION_LIST_VIEW, USER_TRANSACTIONS, modelMap);
    }

    @PostMapping("add")
    public String addNewTransaction(@ModelAttribute("transactionDto") @Valid TransactionDto transactionDto,
                                    BindingResult result, ModelMap modelMap) {
        if (!result.hasErrors()) {
            transactionService.addTransaction(transactionDto);
            return REDIRECT + USER_TRANSACTIONS;
        }

//            for (ObjectError e: result.getAllErrors()) {
//                messageService.addErrorMessage(e.getDefaultMessage());
//            }
//        messageService.addErrorMessage("Transaction Error. Values aren't correct. Please try again.");
//        transactionList(modelMap, new MessageDto("Bad value! = " + transactionDto.getAmount()));
        return REDIRECT + USER_TRANSACTIONS;
    }

    @PostMapping("remove/{id}")
    public String removeTransaction(@PathVariable("id") Integer transId) {
        transactionService.removeById(transId);
        return REDIRECT + USER_TRANSACTIONS;
    }

    @PostMapping("move")
    public String moveCashBetweenAccounts(@ModelAttribute("moveCash") MoveCashDto moveCashDto) {
        transactionService.moveBetweenAccounts(moveCashDto);
        return REDIRECT + USER_TRANSACTIONS;
    }
}
