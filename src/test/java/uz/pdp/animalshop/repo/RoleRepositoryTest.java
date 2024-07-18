package uz.pdp.animalshop.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.pdp.animalshop.entity.Role;
import uz.pdp.animalshop.entity.enums.RoleName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;


    @Test
    void findRolesByName() {
        Role role = new Role();
        role.setName(RoleName.USER);

        Role role1 = new Role();
        role1.setName(RoleName.USER);

        List<Role> roles1 = roleRepository.saveAll(List.of(role, role1));

        assertNotNull(roles1);

        List<Role> roles = roleRepository.findRolesByName("USER");

        assertEquals(2, roles.size());
    }

    @Test
    void findRoleByName() {
        Role role = new Role();
        role.setName(RoleName.USER);
        Role save = roleRepository.save(role);
        assertNotNull(save);

        Role role1 = roleRepository.findRoleByName("USER");
        assertNotNull(role1);

        assertEquals(role.getName(), role1.getName());
    }
}