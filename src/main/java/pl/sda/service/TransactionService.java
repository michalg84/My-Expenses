package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.TransactionDto;
import pl.sda.dto.UserDto;
import pl.sda.model.Transaction;
import pl.sda.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-04-17.
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;


    public List<TransactionDto> getByUserId(Integer id) {
        List<Transaction> list = transactionRepository.findAll();
//        List<Transaction> list = transactionRepository.findAllUsersTransactions(id);
        List<TransactionDto> listDto = new ArrayList<>();
        for (Transaction t : list) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setAccount(t.getAccount());
            transactionDto.setAmount(t.getAmount());
            transactionDto.setFromAccount(t.getFromAccount());
            transactionDto.setId(t.getId());
            transactionDto.setToAccount(t.getToAccount());
            transactionDto.setTransDate(new Date());
            transactionDto.setUserId(t.getId());
            listDto.add(transactionDto);
        }

//        TODO:konwersja trans... na transDto w liście

        return listDto;
    }

    public List<Transaction> getTransactions(UserDto userDto) {
        return transactionRepository.findById(userDto.getId());
    }
}
