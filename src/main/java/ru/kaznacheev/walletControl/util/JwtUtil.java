package ru.kaznacheev.walletControl.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.exception.ExceptionFactory;

import java.time.Duration;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class JwtUtil {

    private static Algorithm algorithm;

    @Value("${security.jwt.secret}")
    private void setAlgorithm(String secret) {
        algorithm = Algorithm.HMAC256(secret);
    }

    public static String generateJwt(User user) {
        return JWT.create()
                .withIssuer("WalletControl")
                .withSubject(user.getUsername())
                .withExpiresAt(Instant.now().plus(Duration.ofMinutes(15)))
                .sign(algorithm);
    }

    public static DecodedJWT verifyJwt(String jwt) {
        JWTVerifier verifier = JWT
                .require(algorithm)
                .withIssuer("WalletControl")
                .build();
        try {
            return verifier.verify(jwt);
        } catch (TokenExpiredException e) {
            throw ExceptionFactory.accessTokenExpired();
        } catch (JWTVerificationException e) {
            throw ExceptionFactory.accessDeniedException();
        }
    }

}
