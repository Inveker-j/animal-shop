package uz.pdp.animalshop.controller;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.animalshop.service.EmailService;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        String email = emailRequest.getToEmail();
        emailService.sendPasswordToEmail(email);
    }
}
