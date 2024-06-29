package uz.pdp.animalshop.service.interfaces;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.Role;
import uz.pdp.animalshop.repo.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findRolesByName(String string) {
        return roleRepository.findRolesByName(string);
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

}
