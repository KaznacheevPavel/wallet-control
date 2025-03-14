package ru.kaznacheev.walletControl.service.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.walletControl.dto.VerificationTokenRequest;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.entity.VerificationToken;
import ru.kaznacheev.walletControl.exception.ExceptionFactory;
import ru.kaznacheev.walletControl.repository.VerificationTokenRepository;
import ru.kaznacheev.walletControl.service.MailSenderService;
import ru.kaznacheev.walletControl.service.VerificationTokenService;

import java.time.Instant;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final MailSenderService mailSenderService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    @Override
    public void createVerificationToken(User user) {
        VerificationToken verificationToken = VerificationToken.builder()
                .user(user)
                .createdAt(Instant.now())
                .build();
        verificationTokenRepository.save(verificationToken);
        sendVerificationMail(user.getEmail(), verificationToken.getToken().toString());
    }

    @Transactional
    @Override
    public void verifyToken(@Valid VerificationTokenRequest verificationTokenRequest) {
        Optional<VerificationToken> verificationToken =
                verificationTokenRepository.findById(UUID.fromString(verificationTokenRequest.getToken()));
        if (verificationToken.isEmpty() ||
                Instant.now().isAfter(verificationToken.get().getCreatedAt().plus(Period.ofDays(1)))) {
            throw ExceptionFactory.invalidVerificationToken();
        }
        verificationToken.get().getUser().setActivated(true);
        verificationTokenRepository.delete(verificationToken.get());
    }

    private void sendVerificationMail(String email, String token) {
        mailSenderService.send(email, "Пожалуйста подтвердите свой email", "http://localhost:8080/api/tokens?token=" + token);
    }

}
