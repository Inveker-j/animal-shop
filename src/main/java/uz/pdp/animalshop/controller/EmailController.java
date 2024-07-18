package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import uz.pdp.animalshop.entity.Role;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.security.JwtUtil;
import uz.pdp.animalshop.service.RoleService;
import uz.pdp.animalshop.service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final Map<String, String> passwordMap = new HashMap<>();
    private final Map<String, Integer> loginAttemptsMap = new HashMap<>();
    private final static Integer MAX_LOGIN_ATTEMPTS = 5;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RoleService roleService;

    @Async
    @PostMapping("/check-password")
    public CompletableFuture<ResponseEntity<?>> checkPassword(
            @RequestParam String code,
            @RequestHeader(name = "Authorization", required = false) String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid token format"));
        }

        token = token.substring(7);
        if (!jwtUtil.isValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid token"));
        }

        String email = jwtUtil.getEmail(token);
        String randomCode = jwtUtil.getClaims(token).get("randomNumber", String.class);

        Integer attempts = loginAttemptsMap.getOrDefault(email, 0);

        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            passwordMap.remove(email);
            loginAttemptsMap.remove(email);
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Resend password"));
        }

        if (randomCode != null && randomCode.equals(code)) {
            loginAttemptsMap.remove(email);
            String firstname = jwtUtil.getClaims(token).get("firstName", String.class);
            String lastname = jwtUtil.getClaims(token).get("lastName", String.class);
            String password = jwtUtil.getClaims(token).get("password", String.class);
            String rolesString = jwtUtil.getClaims(token).get("roles", String.class);

            List<Role> roles = Arrays.stream(rolesString.split(","))
                    .map(roleService::findRoleByName)
                    .collect(Collectors.toList());

            User user = User.builder()
                    .email(email)
                    .password(password)
                    .firstName(firstname)
                    .lastName(lastname)
                    .roles(roles)
                    .build();

            userService.save(user);
            return CompletableFuture.completedFuture(ResponseEntity.ok().body(user));
        }

        attempts++;
        loginAttemptsMap.put(email, attempts);
        return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid password"));
    }
}
