package uz.pdp.animalshop.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import uz.pdp.animalshop.entity.Role;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.security.JwtUtil;
import uz.pdp.animalshop.service.EmailService;
import uz.pdp.animalshop.service.RoleService;
import uz.pdp.animalshop.service.UserService;

import java.util.*;
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
    private final EmailService emailService;


    @PostMapping("/settings/update-password")
    public ResponseEntity<?> updatePassword(
            @RequestParam String randomPassword,
            @RequestParam String sendEmailPassword,
            @RequestParam String password,
            @RequestParam String rePassword,
            @RequestParam String email) {

        Integer attempts = updateLoginAttempts(email, randomPassword, sendEmailPassword);


        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            passwordMap.remove(email);
            loginAttemptsMap.remove(email);
            return ResponseEntity.badRequest().body("Resend password");
        }

        if (randomPassword.equals(sendEmailPassword)) {
            Optional<User> byEmail = userService.findByEmail(email);
            if (byEmail.isEmpty()) {
                return ResponseEntity.badRequest().body("Email doesn't exist");
            }
            User user = byEmail.get();
            if (!password.equals(rePassword)) {
                return ResponseEntity.badRequest().body("Passwords do not match");
            }
            user.setPassword(password);
            userService.save(user);

            return ResponseEntity.ok().body("Password updated successfully");
        }

        return ResponseEntity.badRequest().body("Invalid password");
    }


    @Async
    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestParam String email) {
        ResponseEntity<?> responseEntity = emailService.sendPasswordToEmail(email);
        String sendPassword = (String) responseEntity.getBody();
        System.out.println("sendPassword = " + sendPassword);
        passwordMap.put(email, sendPassword);
        passwordMap.forEach((key, value) -> System.out.println("key: " + key + " value: " + value));

        return ResponseEntity.ok(responseEntity);
    }

//
//    @Async
//    @PostMapping("/check-password")
//    public CompletableFuture<ResponseEntity<?>> checkPassword(
//            @RequestParam String code,
//            @RequestHeader(name = "Authorization", required = false) String token) {
//
//        if (token == null || !token.startsWith("Bearer ")) {
//            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid token format"));
//        }
//
//        token = token.substring(7);
//        if (!jwtUtil.isValid(token)) {
//            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid token"));
//        }
//
//        String email = jwtUtil.getEmail(token);
//        String randomCode = jwtUtil.getClaims(token).get("randomNumber", String.class);
//
//        Integer attempts = loginAttemptsMap.getOrDefault(email, 0);
//
//        if (attempts >= MAX_LOGIN_ATTEMPTS) {
//            passwordMap.remove(email);
//            loginAttemptsMap.remove(email);
//            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Resend password"));
//        }
//
//        if (randomCode != null && randomCode.equals(code)) {
//            loginAttemptsMap.remove(email);
//            String firstname = jwtUtil.getClaims(token).get("firstName", String.class);
//            String lastname = jwtUtil.getClaims(token).get("lastName", String.class);
//            String password = jwtUtil.getClaims(token).get("password", String.class);
//            String rolesString = jwtUtil.getClaims(token).get("roles", String.class);
//
//            List<Role> roles = Arrays.stream(rolesString.split(","))
//                    .map(roleService::findRoleByName)
//                    .collect(Collectors.toList());
//
//            User user = User.builder()
//                    .email(email)
//                    .password(password)
//                    .firstName(firstname)
//                    .lastName(lastname)
//                    .roles(roles)
//                    .build();
//
//            userService.save(user);
//            return CompletableFuture.completedFuture(ResponseEntity.ok().body(user));
//        }
//
//        attempts++;
//        loginAttemptsMap.put(email, attempts);
//
//        return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid password"));
//    }

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

        Integer attempts = updateLoginAttempts(email, randomCode, code);

        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            passwordMap.remove(email);
            loginAttemptsMap.remove(email);
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Resend password"));
        }

        if (randomCode.equals(code)) {
            return handleValidPassword(token, email);
        }

        return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid password"));
    }

    private Integer updateLoginAttempts(String email, String randomCode, String code) {
        Integer attempts = loginAttemptsMap.getOrDefault(email, 0);

        if (!randomCode.equals(code)) {
            attempts++;
            loginAttemptsMap.put(email, attempts);
        }

        return attempts;
    }

    private CompletableFuture<ResponseEntity<?>> handleValidPassword(String token, String email) {
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


}
