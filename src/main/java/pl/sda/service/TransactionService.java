package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.TransactionDto;
import pl.sda.model.Transaction;
import pl.sda.repository.TransactionRepository;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-04-17.
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;


    public List<TransactionDto> getByUserId(Integer id) {
        List<Transaction> list = transactionRepository.findAllUsersTransacrions(id);
//        TODO:konwersja trans... na transDto w liście
        return null;
    }
}
