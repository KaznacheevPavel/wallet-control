package ru.kaznacheev.walletControl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.walletControl.entity.VerificationToken;

import java.util.List;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    @Query(value = "SELECT v.* FROM verification_tokens v WHERE timezone('UTC', CURRENT_TIMESTAMP) > v.created_at + interval '1 day'", nativeQuery = true)
    List<VerificationToken> getExpiredTokens();
}
