package ru.kaznacheev.walletControl.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.walletControl.dto.response.ResponseWithData;
import ru.kaznacheev.walletControl.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseWithData getAllCategories() {
        List<String> categories = categoryService.getAllCategoryTitles();
        return ResponseWithData.builder()
                .title("SUCCESS")
                .status(HttpStatus.OK.value())
                .detail("Список доступных категорий")
                .data(Map.of("categories", categories))
                .build();
    }

}
