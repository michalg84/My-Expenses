package pl.sda.service.account;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

}
