package ru.kaznacheev.walletControl.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.walletControl.dto.VerificationTokenDto;
import ru.kaznacheev.walletControl.dto.response.BaseResponseDto;
import ru.kaznacheev.walletControl.service.VerificationTokenService;

@RestController
@RequestMapping("/api/tokens")
@AllArgsConstructor
public class VerificationTokenController {

    private final VerificationTokenService verificationTokenService;

    @GetMapping
    public BaseResponseDto verifyToken(@RequestParam(name = "token") VerificationTokenDto verificationTokenDto) {
        verificationTokenService.verifyToken(verificationTokenDto);
        return BaseResponseDto.builder()
                .title("SUCCESS_VERIFY")
                .status(HttpStatus.OK.value())
                .detail("Электронная почта успешно подтверждена")
                .build();
    }

}
