package ru.kaznacheev.walletControl.service;

import jakarta.validation.Valid;
import ru.kaznacheev.walletControl.dto.VerificationTokenRequest;
import ru.kaznacheev.walletControl.entity.User;

public interface VerificationTokenService {
    void createVerificationToken(User user);
    void verifyToken(@Valid VerificationTokenRequest verificationTokenRequest);
}
