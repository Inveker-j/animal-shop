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
import uz.pdp.animalshop.repo.AnimalRepository;
import uz.pdp.animalshop.repo.CategoryRepository;
import uz.pdp.animalshop.service.UserService;
import uz.pdp.animalshop.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RunnerClass implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final AnimalRepository animalRepository;
    private final CategoryRepository categoryRepository;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddr;

    @Override
    public void run(String... args) throws Exception {

        List<Category> categories = new ArrayList<>();

        if (ddr.equals("create")) {

            Category dog = new Category();
            dog.setName("Dog");
            Category cat = new Category();
            cat.setName("Cat");
            Category duck = new Category();
            duck.setName("Duck");

            categories.add(dog);
            categories.add(cat);
            categories.add(duck);

            categoryRepository.saveAll(categories);


            Role role = Role.builder().name(RoleName.SUPER_ADMIN).build();

            Role save = roleService.save(role);
            User user = User.builder()
                    .firstName("Oybek")
                    .lastName("Rasulov")
                    .phone("+998972491203")
                    .email("shukurullayevjavoxir777@gmail.com")
                    .roles(List.of(save)).password(("1")).build();
            userService.save(user);
        }


    }
}
