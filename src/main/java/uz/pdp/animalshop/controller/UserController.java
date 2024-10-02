package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.animalshop.dto.SaveUserDTO;
import uz.pdp.animalshop.dto.UserDto;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.security.JwtUtil;
import uz.pdp.animalshop.service.EmailService;
import uz.pdp.animalshop.service.ImageService;
import uz.pdp.animalshop.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final ImageService imageService;

    @PostMapping("/settings/register")
    public ResponseEntity<?> saveUser(@RequestBody SaveUserDTO userDto) {

        if (userDto.getPassword().equals(userDto.getRePassword())) {
            ResponseEntity<?> responseEntity = emailService.sendPasswordToEmail(userDto.getEmail());
            String code = (String) responseEntity.getBody();
            return ResponseEntity.ok().body("Bearer " + jwtUtil.generateRandomAccessToken(userDto, code));
        }
        return ResponseEntity.status(IllegalArgumentException.class.getModifiers()).body("You entered invalid password");
    }

    @PostMapping("/settings/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile image) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !image.isEmpty()) {

            String email = auth.getName();


            Optional<User> optionalUser = userService.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

//                String path = imageService.saveImage(image, user, 3);
                ResponseEntity<?> responseEntity = imageService.saveImage(image, user, 3);
                String path =(String) responseEntity.getBody();
                System.out.println("path = " + path);

                optionalUser.get().setImagePath(path);
                userService.save(user);
                return ResponseEntity.ok().body(user);
            }
        }

        return ResponseEntity.status(IllegalArgumentException.class.getModifiers()).body("You entered invalid image");

    }


    @PutMapping("/settings/edit-user")
    public ResponseEntity<?> editUser(@RequestBody UserDto userDto
            , @RequestParam(name = "image", required = false) MultipartFile image) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return ResponseEntity.notFound().build();
        }

        String email = authentication.getName();
        Optional<User> user = userService.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (image.isEmpty()) {
            user.get().setEmail(userDto.getEmail());
            user.get().setFirstName(userDto.getFirstName());
            user.get().setLastName(userDto.getLastName());

            return ResponseEntity.ok(user);
        } else {

            user.get().setEmail(userDto.getEmail());
            user.get().setFirstName(userDto.getFirstName());
            user.get().setLastName(userDto.getLastName());


            ResponseEntity<?> responseEntity = imageService.saveImage(image, user, 1);
            user.get().setImagePath((String) responseEntity.getBody());
        }
        userService.save(user.orElse(null));

        return ResponseEntity.ok().build();
    }




}
