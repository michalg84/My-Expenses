package dev.galka.service.user;

import dev.galka.account.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role get(int id) {
        return roleRepository.getById(id);
    }
}
