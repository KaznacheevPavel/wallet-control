package ru.kaznacheev.walletControl.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.verify;

@TestPropertySource(properties = "mail.sender-address.test='Application <example@email.com>'")
@ExtendWith(MockitoExtension.class)
class MailSenderServiceImplTest {

    @Mock
    private  JavaMailSender mailSender;
    @Value("${mail.sender-address.test}")
    private String senderAddress;

    @InjectMocks
    private MailSenderServiceImpl mailSenderService;

    @Test
    @DisplayName("Should send mail")
    void givenMailProperties_whenSend_thenSendMail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderAddress);
        mailMessage.setTo("userEmail@email.com");
        mailMessage.setSubject("Registration");
        mailMessage.setText("Подтвердите почту");

        mailSenderService.send("userEmail@email.com", "Registration", "Подтвердите почту");

        verify(mailSender).send(mailMessage);
    }

}