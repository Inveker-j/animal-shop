package uz.pdp.animalshop.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.animalshop.entity.*;
import uz.pdp.animalshop.entity.enums.RoleName;
import uz.pdp.animalshop.repo.CategoryRepository;
import uz.pdp.animalshop.service.AnimalService;
import uz.pdp.animalshop.service.PostService;
import uz.pdp.animalshop.service.UserService;
import uz.pdp.animalshop.service.interfaces.RoleService;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class RunnerClass implements CommandLineRunner {
    private final UserService userService;
    private final AnimalService animalService;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;
    private final CategoryRepository categoryRepository;
    private final RoleService roleService;
    @Value("spring.jpa.hibernate.ddl-auto")
    private String ddr;

    @Override
    public void run(String... args) throws Exception {

        List<Category> categories = new ArrayList<>();


      /*  Animal animal = Animal.builder().name("animal name").build();
        animalService.save(animal);


        Post post = Post.builder().description("description").phone("+998901234567").animal(animal).build();
        postService.save(post);*/

        if (ddr.equals("create")) {
            Category bird = Category.builder()
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

            categoryRepository.saveAll(categories);
        }


//        Role role = Role.builder().name(RoleName.SUPER_ADMIN).build();
//
//        Role save = roleService.save(role);
//        User user = User.builder().email("1")
//                .roles(List.of(save)).password(passwordEncoder.encode("1")).build();
//        userService.save(user);
    }
}
