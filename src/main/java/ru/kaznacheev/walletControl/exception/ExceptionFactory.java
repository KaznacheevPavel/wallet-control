package ru.kaznacheev.walletControl.exception;

import org.springframework.http.HttpStatus;

public final class ExceptionFactory {

    public static BaseApiException invalidUsernameException() {
        return BaseApiException.builder()
                .title("INVALID_USERNAME")
                .detail("Такое имя пользователя недоступно")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    public static BaseApiException invalidEmailException() {
        return BaseApiException.builder()
                .title("INVALID_EMAIL")
                .detail("Такой Email недоступен")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    public static BaseApiException invalidCredentialsException() {
        return BaseApiException.builder()
                .title("INVALID_CREDENTIALS")
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .detail("Неверное имя пользователя или пароль")
                .build();
    }

    public static BaseApiException notActivatedAccount() {
        return BaseApiException.builder()
                .title("NOT_ACTIVATED_ACCOUNT")
                .httpStatus(HttpStatus.FORBIDDEN)
                .detail("Ваш аккаунт не активирован. Подтвердите email для активации")
                .build();
    }

    public static BaseApiException invalidVerificationToken() {
        return BaseApiException.builder()
                .title("INVALID_VERIFICATION_TOKEN")
                .detail("Ссылка недействительна или срок ее действия истек")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    public static BaseApiException accessDeniedException() {
        return BaseApiException.builder()
                .title("ACCESS_DENIED")
                .detail("Доступ запрещен")
                .httpStatus(HttpStatus.FORBIDDEN)
                .build();
    }

    public static BaseApiException accessTokenExpired() {
        return BaseApiException.builder()
                .title("ACCESS_TOKEN_EXPIRED")
                .detail("Срок действия токена истек")
                .httpStatus(HttpStatus.FORBIDDEN)
                .build();
    }

    public static BaseApiException invalidCategory() {
        return BaseApiException.builder()
                .title("INVALID_CATEGORY")
                .detail("Неверное название категории")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    public static BaseApiException userNotFound() {
        return BaseApiException.builder()
                .title("USER_NOT_FOUND")
                .detail("Пользователь не был найден")
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

}
