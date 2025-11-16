package ru.skidivay.auth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skidivay.auth.models.RefreshToken;
import ru.skidivay.auth.repositories.RefreshTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final long refreshTtlSeconds;

    public RefreshTokenService(
            RefreshTokenRepository repository,
            @Value("${jwt.refreshTtlSeconds}") long refreshTtlSeconds
    ) {
        this.repository = repository;
        this.refreshTtlSeconds = refreshTtlSeconds;
    }

    public String create(String userId) {
        String token = UUID.randomUUID().toString();

        RefreshToken rt = new RefreshToken();
        rt.setUserId(userId);
        rt.setToken(token);
        rt.setExpiresAt(Instant.now().plusSeconds(refreshTtlSeconds));
        rt.setRevoked(false);

        repository.save(rt);
        return token;
    }

    public RefreshToken validate(String token) {
        RefreshToken rt = repository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (rt.isRevoked() || rt.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Refresh token expired or revoked");
        }
        return rt;
    }

    public void revoke(String token) {
        Optional<RefreshToken> refreshToken = repository.findByToken(token);

        if (refreshToken.isEmpty()) { return; }

        refreshToken.get().setRevoked(true);
        repository.save(refreshToken.get());
    }

    public void revokeAllForUser(String userId) {
        var tokens = repository.findAllByUserId(userId);
        tokens.forEach(rt -> rt.setRevoked(true));
        repository.saveAll(tokens);
    }

    public void delete(String token) {
        repository.deleteByToken(token);
    }
}
