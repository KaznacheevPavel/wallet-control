package ru.kaznacheev.walletControl.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.walletControl.entity.VerificationToken;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.repository.VerificationTokenRepository;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ExpiredTokenCleanup {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Scheduled(initialDelayString = "PT1M", fixedDelayString = "PT5M")
    @Transactional
    public void deleteExpiredTokens() {
        log.info("TASK: Delete expired tokens");
        List<VerificationToken> expiredTokens = verificationTokenRepository.getExpiredTokens();
        verificationTokenRepository.deleteAllInBatch(expiredTokens);
        userRepository.deleteAllInBatch(expiredTokens.stream().map(VerificationToken::getUser).toList());
    }

}
