package ru.kaznacheev.walletControl.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.walletControl.dto.VerificationTokenRequest;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.entity.VerificationToken;
import ru.kaznacheev.walletControl.exception.BaseApiException;
import ru.kaznacheev.walletControl.repository.VerificationTokenRepository;
import ru.kaznacheev.walletControl.service.MailSenderService;

import java.time.Instant;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationTokenServiceImplTest {

    @Mock
    private MailSenderService mailSenderService;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @InjectMocks
    private VerificationTokenServiceImpl verificationTokenService;

    @Test
    @DisplayName("Should create token and send mail")
    void givenNewUser_whenCreateVerificationToken_thenSaveTokenAndSendMail() {
        User user = User.builder()
                .email("example@email.com")
                .activated(false)
                .build();

        try (MockedStatic<Instant> mockedInstant = mockStatic(Instant.class)) {
            Instant instant = Instant.parse("2025-01-01T12:00:00Z");
            mockedInstant.when(Instant::now).thenReturn(instant);

            VerificationToken expectedVerificationToken = VerificationToken.builder()
                    .token(UUID.fromString("b65f7172-d207-4fea-8133-b8ba4231cfa6"))
                    .user(user)
                    .createdAt(instant)
                    .build();

            when(verificationTokenRepository.save(any(VerificationToken.class))).thenAnswer(invocationOnMock -> {
                VerificationToken savedVerificationToken = invocationOnMock.getArgument(0);
                savedVerificationToken.setToken(UUID.fromString("b65f7172-d207-4fea-8133-b8ba4231cfa6"));
                return savedVerificationToken;
            });

            ArgumentCaptor<VerificationToken> verificationTokenArgumentCaptor = ArgumentCaptor.forClass(VerificationToken.class);

            verificationTokenService.createVerificationToken(user);

            verify(verificationTokenRepository).save(verificationTokenArgumentCaptor.capture());
            VerificationToken actualVerificationToken = verificationTokenArgumentCaptor.getValue();
            assertThat(actualVerificationToken).usingRecursiveComparison().isEqualTo(expectedVerificationToken);

            verify(mailSenderService).send("example@email.com", "Пожалуйста подтвердите свой email",
                    "http://localhost:8080/api/tokens?token=" + "b65f7172-d207-4fea-8133-b8ba4231cfa6");
        }
    }

    @Test
    @DisplayName("Should activate user and delete token")
    void givenVerificationTokenRequest_whenVerifyToken_thenActivateUser() {
        User user = User.builder().activated(false).build();
        VerificationToken verificationToken =
                VerificationToken.builder()
                        .token(UUID.fromString("b65f7172-d207-4fea-8133-b8ba4231cfa6"))
                        .user(user)
                        .createdAt(Instant.now())
                        .build();
        when(verificationTokenRepository.findById(UUID.fromString("b65f7172-d207-4fea-8133-b8ba4231cfa6")))
                .thenReturn(Optional.of(verificationToken));
        VerificationTokenRequest verificationTokenRequest = new VerificationTokenRequest("b65f7172-d207-4fea-8133-b8ba4231cfa6");

        verificationTokenService.verifyToken(verificationTokenRequest);

        assertThat(user.isActivated()).isEqualTo(true);
        verify(verificationTokenRepository).delete(verificationToken);
    }

    @Test
    @DisplayName("Should throw BaseApiException when token is invalid")
    void givenInvalidVerificationTokenRequest_whenVerifyToken_thenThrowException() {
        when(verificationTokenRepository.findById(UUID.fromString("b65f7172-d207-4fea-8133-b8ba4231cfa6"))).thenReturn(Optional.empty());
        VerificationTokenRequest verificationTokenRequest = new VerificationTokenRequest("b65f7172-d207-4fea-8133-b8ba4231cfa6");

        assertThatExceptionOfType(BaseApiException.class)
                .isThrownBy(() -> verificationTokenService.verifyToken(verificationTokenRequest))
                .hasFieldOrPropertyWithValue("title", "INVALID_VERIFICATION_TOKEN");
    }

    @Test
    @DisplayName("Should throw BaseApiException when token expired")
    void givenExpiredVerificationTokenRequest_whenVerifyToken_thenThrowException() {
        VerificationToken verificationToken =
                VerificationToken.builder()
                        .token(UUID.fromString("b65f7172-d207-4fea-8133-b8ba4231cfa6"))
                        .createdAt(Instant.now().minus(Period.ofDays(2)))
                        .build();
        when(verificationTokenRepository.findById(UUID.fromString("b65f7172-d207-4fea-8133-b8ba4231cfa6")))
                .thenReturn(Optional.of(verificationToken));
        VerificationTokenRequest verificationTokenRequest = new VerificationTokenRequest("b65f7172-d207-4fea-8133-b8ba4231cfa6");

        assertThatExceptionOfType(BaseApiException.class)
                .isThrownBy(() -> verificationTokenService.verifyToken(verificationTokenRequest))
                .hasFieldOrPropertyWithValue("title", "INVALID_VERIFICATION_TOKEN");
    }

}