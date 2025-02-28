package ru.kaznacheev.walletControl.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.walletControl.dto.VerificationTokenRequest;
import ru.kaznacheev.walletControl.dto.response.BaseResponse;
import ru.kaznacheev.walletControl.service.VerificationTokenService;

@RestController
@RequestMapping("/api/tokens")
@AllArgsConstructor
public class VerificationTokenController {

    private final VerificationTokenService verificationTokenService;

    @GetMapping
    public BaseResponse verifyToken(@RequestParam(name = "token") VerificationTokenRequest verificationTokenRequest) {
        verificationTokenService.verifyToken(verificationTokenRequest);
        return BaseResponse.builder()
                .title("SUCCESS_VERIFY")
                .status(HttpStatus.OK.value())
                .detail("Электронная почта успешно подтверждена")
                .build();
    }

}
