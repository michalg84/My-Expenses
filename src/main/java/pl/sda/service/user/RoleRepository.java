package pl.sda.service.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Role;


/**
 * Created by Michał Gałka on 2017-04-29.
 */
@Repository
interface RoleRepository extends JpaRepository<Role, Integer> {
}
