package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sda.dto.UserDto;
import pl.sda.model.Account;
import pl.sda.model.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-18.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findById(Integer id);

    @Query("select sum(balance) from Account account where account.user = :user")
    BigDecimal getTotalBallance(@Param("user") User user);

    @Query("from Account account where account.user = :user")
    List<Account> findAll(@Param("user") User user);
}
