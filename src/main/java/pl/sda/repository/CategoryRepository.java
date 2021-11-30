package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sda.model.Category;
import pl.sda.model.User;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByUser(User user);

    @Query("from Category category where category.user = :user")
    List<Category> findAllByUser(@Param("user") User acctualUser);

    @Query("select count(c.id) from Category c where c.name = :name and c.user = :user")
    Integer ifExists(@Param("name") String name, @Param("user") User user);

    @Query("from Category c where c.name = :name and c.user = :user")
    Category findByUserAndName(@Param("name") String name, @Param("user") User user);


}
