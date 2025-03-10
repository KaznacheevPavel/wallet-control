package ru.kaznacheev.walletControl.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.walletControl.dto.NewOperationRequest;
import ru.kaznacheev.walletControl.dto.TimeZoneRequest;
import ru.kaznacheev.walletControl.dto.response.BaseResponse;
import ru.kaznacheev.walletControl.dto.response.OperationForResponse;
import ru.kaznacheev.walletControl.dto.response.ResponseWithData;
import ru.kaznacheev.walletControl.service.OperationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operations")
@AllArgsConstructor
@Slf4j
public class OperationController {

    private final OperationService operationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createOperation(@RequestBody NewOperationRequest newOperationRequest) {
        operationService.createOperation(newOperationRequest);
        return BaseResponse.builder()
                .title("SUCCESS")
                .status(HttpStatus.CREATED.value())
                .detail("Операция успешно добавлена")
                .build();
    }

    @GetMapping
    public ResponseWithData getAllUserOperations(@RequestParam(value = "timeZone") TimeZoneRequest timeZoneRequest) {
        List<OperationForResponse> operations = operationService.getAllUserOperation(timeZoneRequest);
        return ResponseWithData.builder()
                .title("SUCCESS")
                .status(HttpStatus.OK.value())
                .detail("Список операций")
                .data(Map.of("operations", operations))
                .build();
    }

}
