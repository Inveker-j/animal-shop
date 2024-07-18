package uz.pdp.animalshop.repo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.pdp.animalshop.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

        User user1 = User.builder()
                .id(UUID.randomUUID())
                .firstName("Joe")
                .lastName("Smith")
                .email("joe@smith.com")
                .password("password")
                .build();

        User user2 = User.builder()
                .id(UUID.randomUUID())
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .password("password")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testFindById() {

        UUID id = userRepository.availableUsers().get(0).getId();

        Optional<User> optionalUser = userRepository.findById(id);

        assertTrue(optionalUser.isPresent());
        assertEquals("joe@smith.com", optionalUser.get().getEmail());
        assertEquals("password", optionalUser.get().getPassword());
        assertEquals("Joe", optionalUser.get().getFirstName());
        assertEquals("Smith", optionalUser.get().getLastName());
    }

    @Test
    void testFindByEmail() {
        String email = "joe@smith.com";
        Optional<User> optionalUser = userRepository.findByEmail(email);

        assertTrue(optionalUser.isPresent());
        assertEquals("joe@smith.com", optionalUser.get().getEmail());
    }

    @Test
    void testSave() {

        User user3 = User.builder()
                .id(UUID.randomUUID())
                .firstName("Jana")
                .lastName("Moke")
                .email("a@gmail.com")
                .password("password")
                .build();

        userRepository.save(user3);

        List<User> userList = userRepository.availableUsers();
        assertEquals(3, userList.size()); // Assuming setUp() adds 2 users, now should be 3

        User savedUser = userList.get(2);
        assertEquals("a@gmail.com", savedUser.getEmail());
        assertEquals("password", savedUser.getPassword());
        assertEquals("Jana", savedUser.getFirstName());
        assertEquals("Moke", savedUser.getLastName());
    }

    @Test
    void testDeleteById() {
        UUID userId = userRepository.availableUsers().get(0).getId();
        userRepository.deleteById(userId);

        List<User> userList = userRepository.availableUsers();
        assertEquals(1, userList.size()); // Assuming setUp() adds 2 users, now should be 1
    }

}
