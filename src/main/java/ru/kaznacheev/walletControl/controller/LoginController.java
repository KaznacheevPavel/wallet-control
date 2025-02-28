package ru.kaznacheev.walletControl.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.walletControl.dto.LoginRequest;
import ru.kaznacheev.walletControl.dto.response.ResponseWithData;
import ru.kaznacheev.walletControl.service.LoginService;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseWithData login(@RequestBody LoginRequest loginRequest) {
        String jwt = loginService.login(loginRequest);
        return ResponseWithData.builder()
                .title("SUCCESS_AUTHENTICATION")
                .status(HttpStatus.OK.value())
                .detail("Аутентификация успешно пройдена")
                .data(Map.of("access_token", jwt))
                .build();
    }

}
