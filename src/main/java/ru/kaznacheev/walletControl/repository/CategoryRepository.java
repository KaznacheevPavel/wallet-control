package ru.kaznacheev.walletControl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.walletControl.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
