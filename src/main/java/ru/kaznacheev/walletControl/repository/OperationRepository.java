package ru.kaznacheev.walletControl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.walletControl.entity.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
}
