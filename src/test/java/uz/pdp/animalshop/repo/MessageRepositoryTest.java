package uz.pdp.animalshop.repo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.pdp.animalshop.entity.Message;
import uz.pdp.animalshop.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    private User fromUser;
    private User toUser;

    @BeforeEach
    void setUp() {
        fromUser = User.builder()
                .firstName("Joe")
                .lastName("Smith")
                .email("joe@smith.com")
                .password("password")
                .build();

        toUser = User.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .password("password")
                .build();

        userRepository.save(fromUser);
        userRepository.save(toUser);

        Message message1 = Message.builder()
                .from(fromUser)
                .to(toUser)
                .message("hello")
                .isDeletedTo(false)
                .isDeletedFrom(false)
                .build();

        Message message2 = Message.builder()
                .from(fromUser)
                .to(toUser)
                .message("hi")
                .isDeletedTo(false)
                .isDeletedFrom(false)
                .build();

        messageRepository.save(message1);
        messageRepository.save(message2);
    }

    @AfterEach
    void tearDown() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetHistory() {
        List<Message> messages = messageRepository.getHistory(fromUser.getId(), toUser.getId());

        assertEquals(2, messages.size());
        assertEquals("hello", messages.get(0).getMessage());
        assertEquals("hi", messages.get(1).getMessage());
    }

    @Test
    void testDeleteChat() {
        messageRepository.deleteChat(toUser.getId());

        List<Message> messages = messageRepository.getHistory(fromUser.getId(), toUser.getId());
        assertTrue(messages.stream().allMatch(Message::getIsDeletedFrom));
    }
}
