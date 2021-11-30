package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Transaction;
import pl.sda.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Optional<Transaction> findOne(Integer id);

    List<Transaction> findAllByUser(User currentUser);
}
