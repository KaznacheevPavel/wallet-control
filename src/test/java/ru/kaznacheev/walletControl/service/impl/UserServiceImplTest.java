package ru.kaznacheev.walletControl.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kaznacheev.walletControl.dto.NewUserRequest;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.exception.BaseApiException;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.service.VerificationTokenService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private VerificationTokenService verificationTokenService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Should save new user and create verification token")
    void givenNewRequestUser_whenCreateUser_thenSaveUserAndCreateVerificationToken() {
        NewUserRequest newUserRequest = new NewUserRequest("secondUser", "myEmail@gmail.com", "rawPassword");
        when(passwordEncoder.encode("rawPassword")).thenReturn("secret");
        User expectedUser = User.builder()
                .username("secondUser")
                .email("myEmail@gmail.com")
                .password("secret")
                .build();
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        userService.createUser(newUserRequest);

        verify(userRepository).save(userArgumentCaptor.capture());
        User actualUser = userArgumentCaptor.getValue();
        assertThat(actualUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedUser);

        verify(verificationTokenService).createVerificationToken(userArgumentCaptor.capture());
        actualUser = userArgumentCaptor.getValue();
        assertThat(actualUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("Should throw BaseApiException when username is already taken")
    void givenNotUniqueUsername_whenCreateUser_thenThrowException() {
        NewUserRequest newUserRequest = new NewUserRequest("secondUser", "myEmail@gmail.com", "rawPassword");
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThatExceptionOfType(BaseApiException.class)
                .isThrownBy(() -> userService.createUser(newUserRequest))
                .hasFieldOrPropertyWithValue("title", "INVALID_USERNAME");
    }

    @Test
    @DisplayName("Should throw BaseApiException when email is already taken")
    void givenNotUniqueEmail_whenCreateUser_thenThrowException() {
        NewUserRequest newUserRequest = new NewUserRequest("secondUser", "myEmail@gmail.com", "rawPassword");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThatExceptionOfType(BaseApiException.class)
                .isThrownBy(() -> userService.createUser(newUserRequest))
                .hasFieldOrPropertyWithValue("title", "INVALID_EMAIL");
    }


    @Test
    @DisplayName("Should return existing user when username is exist")
    void givenExistingUser_whenGetUser_thenReturnUser() {
        User expectedUser = User.builder()
                .id(53)
                .username("beautifulUsername")
                .email("firstEmail@gmail.com")
                .password(passwordEncoder.encode("secretPassword"))
                .activated(true)
                .build();
        when(userRepository.findByUsername("beautifulUsername")).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getUser("beautifulUsername");

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("Should throw BaseApiException when username is not exist")
    void givenNotExistingUser_whenGetUser_thenThrowException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BaseApiException.class)
                .isThrownBy(() -> userService.getUser("notExistingUsername"))
                .hasFieldOrPropertyWithValue("title", "USER_NOT_FOUND");

    }


}