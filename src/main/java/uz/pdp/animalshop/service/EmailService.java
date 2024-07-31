package uz.pdp.animalshop.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public ResponseEntity<?> sendPasswordToEmail(String toEmail ) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("Please do not share your password with anyone");

            Context context = new Context();

            Random random = new Random();
            int fiveDigitNumber = 10000 + random.nextInt(90000);
            context.setVariable("password", fiveDigitNumber);
            String htmlContent = templateEngine.process("passwordEmailTemplate", context);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

            return ResponseEntity.ok(String.valueOf(fiveDigitNumber));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    protected int generateRandomPassword() {
        Random random = new Random();
        return 10000 + random.nextInt(90000);
    }


}
