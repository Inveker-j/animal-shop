package uz.pdp.animalshop.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.animalshop.entity.Category;
import uz.pdp.animalshop.entity.Role;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.entity.enums.RoleName;
import uz.pdp.animalshop.repo.CategoryRepository;
import uz.pdp.animalshop.service.UserService;
import uz.pdp.animalshop.service.interfaces.RoleService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RunnerClass implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final RoleService roleService;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddr;

    @Override
    public void run(String... args) throws Exception {

     /*   User user = new User();
        user.setEmail("rasulloffoybek449733@gmail.com");
        user.setPassword(passwordEncoder.encode("1"));
        userService.save(user);*/


        List<Category> categories = new ArrayList<>();

        if (ddr.equals("create")) {
//            Role role = new Role();
//            role.setName(RoleName.USER);
//            Role save = roleService.save(role);
//
//            User user1 = User.builder().email("1").password("1").roles(List.of(save)).build();
//        userService.save(user1);
          /*  Category bird = Category.builder()
                    .name("Bird")
                    .build();
            categories.add(bird);
            Category dog = Category.builder()
                    .name("Dog")
                    .build();
            categories.add(dog);
            Category cut = Category.builder()
                    .name("Cat")
                    .build();
            categories.add(cut);

            categoryRepository.saveAll(categories);*/
        Role role = Role.builder().name(RoleName.SUPER_ADMIN).build();

        Role save = roleService.save(role);
        User user = User.builder().email("1")
                .roles(List.of(save)).password(("1")).build();
        userService.save(user);
        }


    }
}
