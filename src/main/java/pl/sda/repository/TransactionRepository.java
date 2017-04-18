package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sda.model.Transaction;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-04-17.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
//
//    @Query("from Transaction t where t.userId := id")
//    List<Transaction> findAllUsersTransactions(@Param("id") Integer id);



}
