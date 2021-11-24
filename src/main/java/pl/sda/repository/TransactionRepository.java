package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Transaction;
import pl.sda.model.User;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findById(Integer id);

    List<Transaction> findAllByUser(User user);
}
