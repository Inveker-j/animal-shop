package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.animalshop.dto.SaveUserDTO;
import uz.pdp.animalshop.dto.UserDtoImpl;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.security.JwtUtil;
import uz.pdp.animalshop.service.EmailService;
import uz.pdp.animalshop.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    @GetMapping("/settings/get-users")
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.availableUsers();

        List<UserDtoImpl> userDtoList = users.stream()
                .map(user -> {
                    UserDtoImpl userDto = new UserDtoImpl();
                    userDto.setEmail(user.getEmail());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());
                    return userDto;
                }).filter(obj -> true).collect(Collectors.toList());
        return ResponseEntity.ok(userDtoList);
    }

    @PostMapping("/settings/register")
    public ResponseEntity<?> saveUser(@RequestBody SaveUserDTO userDto) {

        if (userDto.getPassword().equals(userDto.getRePassword())) {
            return ResponseEntity.ok().body("Bearer " + jwtUtil.generateRandomAccessToken(userDto, emailService.sendPasswordToEmail(userDto.getEmail())));
        }
        return ResponseEntity.status(IllegalArgumentException.class.getModifiers()).body("You entered invalid password");
    }


    @PutMapping("/settings/edit-user")
    public ResponseEntity<?> editUser(@RequestBody UserDtoImpl userDto
            , @RequestParam(name = "image", required = false) MultipartFile image) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return ResponseEntity.notFound().build();
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (image.isEmpty()) {
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
        }
        return ResponseEntity.ok().build();
    }
}
