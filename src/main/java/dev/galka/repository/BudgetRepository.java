package dev.galka.repository;

import dev.galka.account.domain.User;
import dev.galka.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    List<Budget> findAllByUser(User user);

    @Query("from Budget b where b.user = :user and b.year = :year and b.month = :month")
    List<Budget> findAllBy(@Param("user") User user,
                           @Param("year") Integer year,
                           @Param("month") Integer month);
}
