package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.AccountType;

/**
 * Created by Michał Gałka on 2017-05-19.
 */
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

}
