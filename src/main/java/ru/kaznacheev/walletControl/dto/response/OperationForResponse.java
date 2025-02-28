package ru.kaznacheev.walletControl.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class OperationForResponse {
    @JsonProperty(value = "created_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime createdAt;
    private final String category;
    private final String type;
    private final BigDecimal amount;
    private final String description;
}
