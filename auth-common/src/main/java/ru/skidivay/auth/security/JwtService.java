package ru.skidivay.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final Key key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.accessTtlSeconds}") long accessTtlSeconds,
            @Value("${jwt.refreshTtlSeconds}") long refreshTtlSeconds
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
    }

    private String issue(String subject, Map<String, Object> claims, long ttlSeconds) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(ttlSeconds)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String issueAccess(String subject, Map<String, Object> claims) {
        Map<String, Object> allClaims = new HashMap<>(claims);
        allClaims.put("type", "access");
        return issue(subject, allClaims, accessTtlSeconds);
    }

    public String issueRefresh(String subject) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return issue(subject, claims, refreshTtlSeconds);
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public boolean isRefreshToken(Jws<Claims> jws) {
        return "refresh".equals(jws.getBody().get("type", String.class));
    }

    public boolean isAccessToken(Jws<Claims> jws) {
        return "access".equals(jws.getBody().get("type", String.class));
    }
}
