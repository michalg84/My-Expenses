package dev.galka.service.account;

import dev.galka.account.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

}
