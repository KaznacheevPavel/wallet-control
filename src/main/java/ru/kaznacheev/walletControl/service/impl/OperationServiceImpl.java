package ru.kaznacheev.walletControl.service.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.walletControl.dto.NewOperationRequest;
import ru.kaznacheev.walletControl.dto.TimeZoneRequest;
import ru.kaznacheev.walletControl.dto.response.OperationForResponse;
import ru.kaznacheev.walletControl.entity.Category;
import ru.kaznacheev.walletControl.entity.Operation;
import ru.kaznacheev.walletControl.entity.OperationType;
import ru.kaznacheev.walletControl.repository.OperationRepository;
import ru.kaznacheev.walletControl.service.CategoryService;
import ru.kaznacheev.walletControl.service.OperationService;
import ru.kaznacheev.walletControl.service.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    @Transactional
    @Override
    public void createOperation(@Valid NewOperationRequest newOperationRequest) {
        Category category = categoryService.getCategory(newOperationRequest.getCategory());
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Operation operation = Operation.builder()
                .user(userService.getUser(username))
                .category(category)
                .createdAt(Instant.now())
                .type(OperationType.valueOf(newOperationRequest.getType()))
                .amount(newOperationRequest.getAmount())
                .description(newOperationRequest.getDescription())
                .build();
        operationRepository.save(operation);
    }


    @Transactional(readOnly = true)
    @Override
    public List<OperationForResponse> getAllUserOperation(@Valid TimeZoneRequest timeZoneRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return operationRepository.findAllByUser(userService.getUser(username)).stream().map(operation -> {
            return OperationForResponse.builder()
                    .createdAt(LocalDateTime.ofInstant(operation.getCreatedAt(), ZoneId.of(timeZoneRequest.getTimeZone())))
                    .category(operation.getCategory().getTitle())
                    .type(operation.getType().name())
                    .amount(operation.getAmount())
                    .description(operation.getDescription())
                    .build();
        }).toList();
    }

}
