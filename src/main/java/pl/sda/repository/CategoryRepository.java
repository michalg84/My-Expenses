package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Category;

/**
 * Created by Michał Gałka on 2017-05-22.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
