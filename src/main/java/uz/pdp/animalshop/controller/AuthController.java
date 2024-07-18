package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.pdp.animalshop.dto.LoginDTO;
import uz.pdp.animalshop.dto.TokenDTO;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.security.JwtUtil;
import uz.pdp.animalshop.service.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginDTO testDto) {
        var auth = new UsernamePasswordAuthenticationToken(testDto.getEmail(), testDto.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(auth);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            TokenDTO tokenDTO = new TokenDTO(
                    "Bearer " + jwtUtil.generateToken(userDetails),
                    "Bearer " + jwtUtil.generateRefreshToken(userDetails)
            );

            return ResponseEntity.ok(tokenDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getMe() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (email.equals("anonymousUser") || email.isBlank()) {
            return ResponseEntity.badRequest().body("Anonymous user");
        }

        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {

            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("User not found");
    }
}
