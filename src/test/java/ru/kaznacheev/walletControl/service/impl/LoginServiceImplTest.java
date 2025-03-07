package ru.kaznacheev.walletControl.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kaznacheev.walletControl.dto.LoginRequest;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.exception.BaseApiException;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.util.JwtUtil;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    @DisplayName("Should generate jwt when user login")
    void givenCredentials_whenLogin_thenGenerateJwt() {
        User expectedUser = User.builder()
                .username("firstUser")
                .password("secret")
                .activated(true)
                .build();
        LoginRequest loginRequest = new LoginRequest("firstUser", "rawPassword");
        when(passwordEncoder.matches("rawPassword", "secret")).thenReturn(true);
        when(userRepository.findByUsername("firstUser")).thenReturn(Optional.of(expectedUser));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
            loginService.login(loginRequest);
            mockedJwtUtil.verify(() -> JwtUtil.generateJwt(userArgumentCaptor.capture()));

            User actualUser = userArgumentCaptor.getValue();
            assertThat(actualUser)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedUser);
        }
    }

    @Test
    @DisplayName("Should throw BaseApiException when user is not exists")
    void givenNotExistingUser_whenLogin_thenThrowException() {
        LoginRequest loginRequest = new LoginRequest("notExistingUsername", "rawPassword");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BaseApiException.class)
                .isThrownBy(() -> loginService.login(loginRequest))
                .hasFieldOrPropertyWithValue("title", "INVALID_CREDENTIALS");
    }

    @Test
    @DisplayName("Should throw BaseApiException when password is incorrect")
    void givenIncorrectPassword_whenLogin_thenThrowException() {
        User user = User.builder()
                .username("firstUser")
                .password("secret")
                .build();
        when(userRepository.findByUsername("firstUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("incorrectPassword", "secret")).thenReturn(false);
        LoginRequest loginRequest = new LoginRequest("firstUser", "incorrectPassword");

        assertThatExceptionOfType(BaseApiException.class)
                .isThrownBy(() -> loginService.login(loginRequest))
                .hasFieldOrPropertyWithValue("title", "INVALID_CREDENTIALS");
    }

    @Test
    @DisplayName("Should throw BaseApiException when user is not activated")
    void givenNotActivatedUser_whenLogin_thenThrowException() {
        User user = User.builder()
                .username("firstUser")
                .password("secret")
                .activated(false)
                .build();
        when(userRepository.findByUsername("firstUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rawPassword", "secret")).thenReturn(true);
        LoginRequest loginRequest = new LoginRequest("firstUser", "rawPassword");

        assertThatExceptionOfType(BaseApiException.class)
                .isThrownBy(() -> loginService.login(loginRequest))
                .hasFieldOrPropertyWithValue("title", "NOT_ACTIVATED_ACCOUNT");
    }


}