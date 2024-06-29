package uz.pdp.animalshop.controller;


import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import uz.pdp.animalshop.dto.EmailRequest;
import uz.pdp.animalshop.service.EmailService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    private final Map<String, String> passwordMap = new HashMap<>();
    private final Map<String, Integer> loginAttemptsMap = new HashMap<>();
    private final Integer MAX_LOGIN_ATTEMPTS = 5;

    @Async
    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        String email = emailRequest.getToEmail();
        String sendPassword = emailService.sendPasswordToEmail(email);
        passwordMap.put(email, sendPassword);
        passwordMap.forEach((key, value) -> System.out.println("key: " + key + " value: " + value));

        return sendPassword;
    }

    @Async
    @PostMapping("/check-password")
    public ResponseEntity<?> checkPassword(@RequestParam String email, @RequestParam String password) {
        String storedPassword = passwordMap.get(email);
        Integer attempts = loginAttemptsMap.get(email);

        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            passwordMap.remove(email);
            return ResponseEntity.badRequest().body("Resend password");
        }

        System.out.println("storedPassword = " + email + "  /check-password " + storedPassword);
        if (storedPassword != null && storedPassword.equals(password)) {
            passwordMap.remove(email);
            loginAttemptsMap.remove(email);
            return ResponseEntity.ok().body(storedPassword);
        } else {
            attempts++;
            loginAttemptsMap.put(email, attempts);
            System.out.println("password = " + password);
            return ResponseEntity.internalServerError().body("Invalid password");
        }
    }
}
