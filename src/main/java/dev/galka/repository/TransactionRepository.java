package dev.galka.repository;

import dev.galka.account.domain.User;
import dev.galka.model.TransactionDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDbEntity, Integer> {

    List<TransactionDbEntity> findAllByUser(User currentUser);
}
