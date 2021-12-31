package dev.galka.controler;

import dev.galka.account.domain.AccountApi;
import dev.galka.dto.CategoryDto;
import dev.galka.dto.MoveCashDto;
import dev.galka.dto.TransactionDto;
import dev.galka.service.CategoryService;
import dev.galka.service.TransactionService;
import dev.galka.service.account.AccountDto;
import dev.galka.service.account.AccountIdNameDtoView;
import dev.galka.service.account.AccountService;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.user.UserDto;
import dev.galka.service.user.UserMapper;
import dev.galka.service.webnotification.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    private AccountApi accountApi;
    @Autowired
    private AuthUserProvider authUserProvider;
    @Autowired
    private CategoryService categoryService;
    private static final String REDIRECT = "redirect:/";

    @GetMapping("list")
    public ModelAndView transactionList(ModelMap modelMap) {
        List<AccountDto> accounts = accountApi.find();
        final List<AccountIdNameDtoView> accountIdNameDtoViews = accounts.stream().map(dto -> dto.createIdNameDto()).collect(Collectors.toList());
        if (accounts.isEmpty()) {
            messageService.addWarnMessage(CREATE_ACCOUNT_FIRST);
        }
        final UserDto userDto = UserMapper.map(authUserProvider.authenticatedUser());
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("newCategory", new CategoryDto());
        modelMap.addAttribute("accounts", accountIdNameDtoViews); //todo - check what values are used in FrontEnd -> id and names
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
