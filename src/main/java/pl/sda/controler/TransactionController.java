package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.CategoryDto;
import pl.sda.dto.MoveCashDto;
import pl.sda.dto.TransactionDto;
import pl.sda.model.User;
import pl.sda.service.CategoryService;
import pl.sda.service.TransactionService;
import pl.sda.service.account.AccountDto;
import pl.sda.service.account.AccountService;
import pl.sda.service.user.AuthUserProvider;
import pl.sda.service.user.UserDto;
import pl.sda.service.user.UserMapper;
import pl.sda.service.user.UserService;
import pl.sda.service.webnotification.MessageService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private static final String CREATE_ACCOUNT_FIRST = "You need to create cash account before making transactions.";
    private static final String USER_TRANSACTIONS = "transaction/list";
    private static final String USER_TRANSACTION_LIST_VIEW = "user/list";

    @Autowired
    private MessageService messageService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthUserProvider authUserProvider;
    @Autowired
    private CategoryService categoryService;
    private String REDIRECT = "redirect:/";

    @GetMapping("list")
    public ModelAndView transactionList(ModelMap modelMap) {
        List<AccountDto> accounts = accountService.getAccounts();
        if (accounts.isEmpty()) {
            messageService.addWarnMessage(CREATE_ACCOUNT_FIRST);
        }
        final UserDto userDto = UserMapper.map(authUserProvider.authenticatedUser());
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
