    package uz.pdp.animalshop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.animalshop.entity.Message;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.repo.MessageRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MessageService messageService;

    @InjectMocks
    private UserService userService;

    private List<Message> messages;

    private User fromUser;
    private User toUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fromUser = User.builder()
                .firstName("Joe")
                .lastName("Smith")
                .email("joe@smith.com")
                .password(passwordEncoder.encode("password"))
                .build();

        toUser = User.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .password(passwordEncoder.encode("password"))
                .build();

        userService.save(fromUser);
        userService.save(toUser);

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

        messages = Arrays.asList(message1, message2);

        when(messageRepository.findAll()).thenReturn(messages);
        when(messageRepository.findById(message1.getId())).thenReturn(Optional.of(message1));
        when(messageRepository.findById(UUID.randomUUID())).thenReturn(Optional.empty());
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> {
            Message savedMessage = invocation.getArgument(0);
            savedMessage.setId(UUID.randomUUID()); // simulate saving with generated UUID
            return savedMessage;
        });
        doNothing().when(messageRepository).deleteById(any(UUID.class));
        when(messageRepository.getHistory(fromUser.getId(), toUser.getId())).thenReturn(messages);
    }

    @AfterEach
    void tearDown() {
        reset(messageRepository);
    }

    @Test
    void testFindAll() {
        List<Message> foundMessages = messageService.findAll();
        assertEquals(messages.size(), foundMessages.size());
        assertEquals(messages, foundMessages);
    }

    @Test
    void testFindById_existingId() {
        Optional<Message> foundMessage = messageService.findById(messages.get(0).getId());
        assertEquals(messages.get(0), foundMessage.orElse(null));
    }

    @Test
    void testFindById_nonExistingId() {
        Optional<Message> foundMessage = messageService.findById(UUID.randomUUID());
        assertEquals(Optional.empty(), foundMessage);
    }

    @Test
    void testSave() {

        Message message1 = Message.builder()
                .from(fromUser)
                .to(toUser)
                .message("hello")
                .isDeletedTo(false)
                .isDeletedFrom(false)
                .build();

        ResponseEntity<?> response = messageService.save(message1);
        assertEquals(200, response.getStatusCodeValue());
        Message savedMessage = (Message) response.getBody();
        assertEquals(message1.getMessage(), savedMessage.getMessage());
        assertEquals(fromUser, savedMessage.getFrom());
        assertEquals(toUser, savedMessage.getTo());
    }

    @Test
    void testDelete() {
        UUID messageId = messages.get(0).getId();
        messageService.delete(messageId);
        verify(messageRepository, times(1)).deleteById(messageId);
    }

    @Test
    void testGetHistory() {
        List<Message> history = messageService.getHistory(fromUser.getId(), toUser.getId());
        assertEquals(messages.size(), history.size());
        assertEquals(messages, history);
    }

    @Test
    public void testDeleteChat() {
        UUID toId = UUID.randomUUID();

        messageService.deleteChat(toId);

        verify(messageRepository, times(1)).deleteChat(toId);
    }


}
