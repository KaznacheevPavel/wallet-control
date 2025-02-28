package ru.kaznacheev.walletControl.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.walletControl.dto.NewUserRequest;
import ru.kaznacheev.walletControl.dto.response.BaseResponse;
import ru.kaznacheev.walletControl.service.UserService;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createUser(@RequestBody NewUserRequest newUserRequest) {
        userService.createUser(newUserRequest);
        return BaseResponse.builder()
                .title("SUCCESS_REGISTRATION")
                .status(HttpStatus.CREATED.value())
                .detail("Аккаунт успешно зарегистрирован. " +
                        "На вашу почту отправлено письмо с подтверждением, оно доступно 24 часа")
                .build();
    }

}
