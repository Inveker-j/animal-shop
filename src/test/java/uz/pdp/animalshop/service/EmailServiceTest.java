package uz.pdp.animalshop.service;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendPasswordToEmail() throws Exception {
        String toEmail = "test@example.com";
        String template = "passwordEmailTemplate";
        String expectedPassword = "12345";
        String sentPassword = "12345";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        when(templateEngine.process(eq(template), any(Context.class)))
                .thenAnswer(invocation -> {
                    Context context = invocation.getArgument(1);
                    assertEquals(expectedPassword, context.getVariable("password").toString());
                    return "Email Content";
                });



        EmailService emailServiceSpy = spy(emailService);
        doReturn(Integer.parseInt(expectedPassword)).when(emailServiceSpy).generateRandomPassword();

        String returnedPassword = emailServiceSpy.sendPasswordToEmail(toEmail);

        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender, times(1)).send(mimeMessageCaptor.capture());

        assertEquals( "12345",any());
//        verify(emailServiceSpy, times(1)).generateRandomPassword();
    }
}
