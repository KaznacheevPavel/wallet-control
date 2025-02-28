package ru.kaznacheev.walletControl.service;

import ru.kaznacheev.walletControl.entity.Category;

import java.util.List;

public interface CategoryService {
    Category getCategory(String title);
    List<String> getAllCategoryTitles();
}
