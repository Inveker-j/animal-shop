package uz.pdp.animalshop.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import uz.pdp.animalshop.entity.Animal;
import uz.pdp.animalshop.entity.enums.Gander;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;


    @Test
    void deleteById() {
        Animal animal = Animal.builder()
                .name("Test")
                .gander(Gander.MALE)
                .build();
        Animal save = animalRepository.save(animal);
        assertNotNull(save);

        animalRepository.deleteById(save.getId());

        Optional<Animal> animalOptional = animalRepository.findById(save.getId());
        assertTrue(animalOptional.isPresent());
    }
}