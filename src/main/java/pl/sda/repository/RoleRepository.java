package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Role;


/**
 * Created by Michał Gałka on 2017-04-29.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {


}
