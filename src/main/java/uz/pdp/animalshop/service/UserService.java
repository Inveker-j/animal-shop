package uz.pdp.animalshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.repo.UserRepository;
import uz.pdp.animalshop.service.interfaces.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<User, UUID> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }


    @Override
    public ResponseEntity<?> save(User user) {

        if (user == null || user.getEmail() == null || user.getPassword() == null || user.getFirstName() == null || user.getLastName() == null ||

                user.getEmail().isBlank() || user.getPassword().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email or password or first name or last name");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<User> findUser = findByEmail(user.getEmail());

        if (findUser.isPresent()) {
            return ResponseEntity.ok().body("Are you already sign up");
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    @Override
    public void delete(UUID uuid) {
        userRepository.deleteById(uuid);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    public List<User> availableUsers() {
//        return userRepository.availableUsers();
//    }

    public Optional<User> findAvailableUserById(UUID userId) {
        return userRepository.findAvailableUserByUserId(userId);
    }

}
