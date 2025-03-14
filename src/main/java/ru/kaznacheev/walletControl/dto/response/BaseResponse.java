package ru.kaznacheev.walletControl.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BaseResponse {
    private final String title;
    private final int status;
    private final String detail;
}
