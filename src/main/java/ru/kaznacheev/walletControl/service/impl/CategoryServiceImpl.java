package ru.kaznacheev.walletControl.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.walletControl.entity.Category;
import ru.kaznacheev.walletControl.exception.ExceptionFactory;
import ru.kaznacheev.walletControl.repository.CategoryRepository;
import ru.kaznacheev.walletControl.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public Category getCategory(String title) {
        Optional<Category> category = categoryRepository.findByTitle(title);
        if (category.isEmpty()) {
            throw ExceptionFactory.invalidCategory();
        }
        return category.get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllCategoryTitles() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(Category::getTitle).toList();
    }

}
