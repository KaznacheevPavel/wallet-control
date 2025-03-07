package ru.kaznacheev.walletControl.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.walletControl.entity.Category;
import ru.kaznacheev.walletControl.exception.BaseApiException;
import ru.kaznacheev.walletControl.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Should throw BaseApiException when category title is not exist")
    void givenInvalidCategory_whenGetCategory_thenThrowException() {
        when(categoryRepository.findByTitle("Название")).thenReturn(Optional.empty());
        assertThatExceptionOfType(BaseApiException.class)
                .isThrownBy(() -> categoryService.getCategory("Название"))
                .hasFieldOrPropertyWithValue("title", "INVALID_CATEGORY");
    }

    @Test
    @DisplayName("Should return existing Category when category title is exists")
    void givenValidCategory_whenGetCategory_thenGetExistingCategory() {
        Category expectedCategory = Category.builder().title("Авто").build();
        when(categoryRepository.findByTitle("Авто")).thenReturn(Optional.of(expectedCategory));
        Category actualCategory = categoryService.getCategory("Авто");
        assertThat(actualCategory).isEqualTo(expectedCategory);
    }

    @Test
    @DisplayName("Should return list of existing category titles")
    void givenListOfCategories_whenGetAllCategoryTitles_thenGetListOfTitles() {
        List<Category> categories = List.of(
                Category.builder().title("Авто").build(),
                Category.builder().title("Продукты").build(),
                Category.builder().title("Одежда").build(),
                Category.builder().title("Такси").build()
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        List<String> expectedTitles = List.of("Авто", "Продукты", "Одежда", "Такси");
        List<String> actualTitles = categoryService.getAllCategoryTitles();
        assertThat(actualTitles).isEqualTo(expectedTitles);
    }
}