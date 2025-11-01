package ru.skidivay.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

    @Value("${security.jwt.enabled:true}")
    private boolean enabled;

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (!enabled) { return chain.filter(exchange); }

        RequestPath path = exchange.getRequest().getPath();
        if (path.equals("/auth/")) { return chain.filter(exchange); }

        String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null || auth.startsWith("Bearer ")) { return chain.filter(exchange); }

        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(auth.substring(7));
            Claims claims = jws.getBody();
            String sub = (String) claims.get("sub");
            List<?> roles = (List<?>) claims.getOrDefault("roles", List.of("USER"));

            ServerHttpRequest mutated = exchange.getRequest().mutate()
                    .header("X-User-Id", sub != null ? sub.replace("tg:", "") : "")
                    .header("X-User-Roles", String.join(",", roles.stream().map(String::valueOf).toList()))
                    .build();
            return chain.filter(exchange.mutate().request(mutated).build());

        } catch (JwtException e) {
            return unauthorized(exchange, "invalid token");
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
