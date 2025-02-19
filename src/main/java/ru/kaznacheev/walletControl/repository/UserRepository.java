package ru.kaznacheev.walletControl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.walletControl.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
