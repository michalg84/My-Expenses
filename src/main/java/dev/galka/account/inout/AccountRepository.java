package dev.galka.account.inout;

import dev.galka.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountDbEntity, Integer> {

    List<AccountDbEntity> findByUserId(Integer id);

    @Query("select sum(balance) from AccountDbEntity account where account.user = :user")
    BigDecimal getTotalBalance(@Param("user") User user);

}
