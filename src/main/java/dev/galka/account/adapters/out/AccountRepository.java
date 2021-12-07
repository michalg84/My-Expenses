package dev.galka.account.adapters.out;

import dev.galka.account.domain.model.Account;
import dev.galka.account.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findByUserId(Integer id);


    @Query("select sum(balance) from Account account where account.user = :user")
    BigDecimal getTotalBalance(@Param("user") User user);

}
