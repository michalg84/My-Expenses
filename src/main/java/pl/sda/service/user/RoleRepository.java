package pl.sda.service.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Role;

@Repository
interface RoleRepository extends JpaRepository<Role, Integer> {
}
