package uz.pdp.animalshop.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.animalshop.entity.Role;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.entity.enums.RoleName;
import uz.pdp.animalshop.service.UserService;
import uz.pdp.animalshop.service.interfaces.RoleService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RunnerClass implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
//        Role role = Role.builder().name(RoleName.SUPER_ADMIN).build();
//
//        Role save = roleService.save(role);
//        User user = User.builder().email("1")
//                .role(List.of(save)).password(passwordEncoder.encode("1")).build();
//        userService.save(user);
    }
}
