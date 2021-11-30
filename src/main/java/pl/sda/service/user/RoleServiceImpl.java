package pl.sda.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.model.Role;

import java.util.Optional;

@Service
class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role get(int id) {
        return roleRepository.getById(id);
    }
}
