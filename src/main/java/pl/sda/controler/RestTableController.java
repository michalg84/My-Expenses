package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.dto.TransactionDto;
import pl.sda.dto.UserDto;
import pl.sda.model.Transaction;
import pl.sda.service.TransactionService;
import pl.sda.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/table")
public class RestTableController {

    @Autowired
    private TransactionService trasactionService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/transaction", produces = "application/json")
    public UserDto getTransactions() {
//        List<Transaction> list = userService.getAcctualUser().getTransactionList();
//        List<TransactionDto> l = trasactionService.getTransactionsWithBalance(list);
//        return userService.getAcctualUserDto();

        return userService.getUser();
    }


}