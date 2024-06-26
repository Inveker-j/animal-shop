package uz.pdp.animalshop.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendPasswordToEmail(String toEmail ) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("shukurullayevjavoxir777@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Please do not share your password with anyone");

            // Create the HTML content
            Context context = new Context();

            Random random = new Random();
            int sixDigitNumber = 100000 + random.nextInt(900000);
            context.setVariable("password", sixDigitNumber);
            String htmlContent = templateEngine.process("passwordEmailTemplate", context);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

            System.out.println("Mail successfully sent to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
}
