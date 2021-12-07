package dev.galka.service.user;

import dev.galka.account.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends JpaRepository<User, Integer> {

    @Query("from User u where u.mail = :mail")
    User findUserByMail(@Param("mail") String mail);

    User findByUsername(String username);


}
