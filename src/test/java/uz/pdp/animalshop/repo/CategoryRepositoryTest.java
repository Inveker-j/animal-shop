package uz.pdp.animalshop.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import uz.pdp.animalshop.entity.Category;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void deleteById() {
        Category category = new Category();
        category.setName("Test");
        category.setIsDeleted(false);


        Category save = categoryRepository.save(category);
        assertNotNull(save);

        categoryRepository.deleteById(save.getId());
        Optional<Category> optionalCategory =
                categoryRepository.findById(save.getId());

        assertTrue(optionalCategory.isPresent());
    }
}