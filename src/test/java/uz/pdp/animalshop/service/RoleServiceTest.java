package uz.pdp.animalshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uz.pdp.animalshop.entity.Role;
import uz.pdp.animalshop.entity.enums.RoleName;
import uz.pdp.animalshop.repo.RoleRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceTest {
    private RoleService roleService;
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        this.roleRepository = Mockito.mock(RoleRepository.class);
        this.roleService = new RoleService(roleRepository);
    }

    @Test
    void save() {
        Role role = new Role();
        role.setName(RoleName.USER);
        Mockito.when(roleRepository.save(role)).thenReturn(role);

        Role savedRole = roleService.save(role);
        assertNotNull(savedRole);

        assertEquals(RoleName.USER, savedRole.getName());
    }

    @Test
    void findRolesByName() {
        Role role = new Role();
        role.setName(RoleName.USER);

        Role role2 = new Role();
        role2.setName(RoleName.USER);

        Mockito.when(roleRepository.save(role)).thenReturn(role);
        Mockito.when(roleRepository.save(role2)).thenReturn(role2);

        Role savedRole = roleService.save(role);
        Role savedRole2 = roleService.save(role2);


        List<Role> roles = List.of(role, savedRole, savedRole2);

        Mockito.when(roleService.findRolesByName(String.valueOf(RoleName.USER))).thenReturn(roles);

        List<Role> roles2 = roleService.findRolesByName(String.valueOf(RoleName.USER));

        assertEquals(roles.size(), roles2.size());
        assertEquals(roles.get(0), roles2.get(0));

    }

    @Test
    void findRoleByName() {
        Role role = new Role();
        role.setName(RoleName.USER);


        Mockito.when(roleRepository.save(role)).thenReturn(role);


        Role savedRole = roleService.save(role);

        Mockito.when(roleService.findRoleByName(String.valueOf(RoleName.USER))).thenReturn(role);
        Role roleByName = roleRepository.findRoleByName(String.valueOf(RoleName.USER));
        assertNotNull(roleByName);

        assertEquals(role.getName(), savedRole.getName());

    }
}