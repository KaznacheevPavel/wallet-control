package ru.kaznacheev.walletControl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "verification_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "verification_token")
    private UUID token;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "created_at")
    private Instant createdAt;

}
