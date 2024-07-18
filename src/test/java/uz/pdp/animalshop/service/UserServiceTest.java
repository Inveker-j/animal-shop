package uz.pdp.animalshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.repo.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@ExtendWith((MockitoExtension.class))
public class UserServiceTest {

    //    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void before() {
        this.passwordEncoder = Mockito.mock(PasswordEncoder.class);
        this.userRepository = Mockito.mock(UserRepository.class);
        this.userService = new UserService(userRepository, this.passwordEncoder);

        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("plainPassword");
        user.setFirstName("John");
        user.setLastName("Doe");
    }

    @Test
    void test_UserSave() {

        String encodePassword = "encodePassword";
        when(passwordEncoder.encode(any(String.class))).thenReturn(encodePassword);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = userService.save(user);
        assertEquals(user, response.getBody());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUser_AlreadyExists() {

        String encodePassword = "encodePassword";
        when(passwordEncoder.encode(any(String.class))).thenReturn(encodePassword);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        ResponseEntity<?> response = userService.save(user);
        assertEquals("Are you already sign up", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testSave_InvalidData() {
        user.setEmail("");
        ResponseEntity<?> response = userService.save(user);
        assertEquals("Invalid email or password or first name or last name", response.getBody());

        user.setEmail("test@example.com");
        user.setPassword("");
        response = userService.save(user);
        assertEquals("Invalid email or password or first name or last name", response.getBody());

        user.setPassword("plainPassword");
        user.setFirstName("");
        response = userService.save(user);
        assertEquals("Invalid email or password or first name or last name", response.getBody());

        user.setFirstName("John");
        user.setLastName("");
        response = userService.save(user);
        assertEquals("Invalid email or password or first name or last name", response.getBody());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testFindById_UserExist() {

        UUID userId = user.getId();

        when(userRepository.findAvailableUserByUserId(userId)).thenReturn(Optional.of(user));

        Optional<User> optionalUser = userService.findAvailableUserById(userId);

        assertTrue(optionalUser.isPresent());
        assertEquals(user.getEmail(), optionalUser.get().getEmail());
        assertEquals(user.getPassword(), optionalUser.get().getPassword());
        assertEquals(user.getFirstName(), optionalUser.get().getFirstName());
        assertEquals(user.getLastName(), optionalUser.get().getLastName());
        assertNotNull(user.getLastName());

        verify(userRepository, times(1)).findAvailableUserByUserId(userId);
    }

    @Test
    void testSave_NullData() {


        user.setEmail(null);
        ResponseEntity<?> response = userService.save(user);
        assertEquals("Invalid email or password or first name or last name", response.getBody());

        user.setEmail("test@example.com");
        user.setPassword(null);
        response = userService.save(user);
        assertEquals("Invalid email or password or first name or last name", response.getBody());

        user.setPassword("plainPassword");
        user.setFirstName(null);
        response = userService.save(user);
        assertEquals("Invalid email or password or first name or last name", response.getBody());

        user.setFirstName("John");
        user.setLastName(null);
        assertNull(user.getLastName());
        response = userService.save(user);
        assertEquals("Invalid email or password or first name or last name", response.getBody());

        user = null;
        response = userService.save(user);
        assertEquals("Invalid email or password or first name or last name", response.getBody());

        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void testFindById_UserNotExist() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Optional<User> optionalUser = userService.findById(userId);
        assertTrue(optionalUser.isEmpty());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindAvailableUserById_UserExists() {
        UUID userId = user.getId();
        when(userRepository.findAvailableUserByUserId(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findAvailableUserById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        assertEquals(user.getPassword(), foundUser.get().getPassword());
        assertEquals(user.getFirstName(), foundUser.get().getFirstName());
        assertEquals(user.getLastName(), foundUser.get().getLastName());

        verify(userRepository, times(1)).findAvailableUserByUserId(userId);
    }

    @Test
    void testFindAvailableUserById_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findAvailableUserByUserId(userId)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findAvailableUserById(userId);

        assertFalse(foundUser.isPresent());

        verify(userRepository, times(1)).findAvailableUserByUserId(userId);
    }

    @Test
    void testDelete() {
        UUID userId = UUID.randomUUID();

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void findAll(){
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setEmail("test1@example.com");
        user1.setPassword("plainPassword");

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setEmail("test2@example.com");
        user2.setPassword("plainPassword");

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);


        List<User> result = userService.findAll();


        assertEquals(users.size(), result.size());
    }
}
