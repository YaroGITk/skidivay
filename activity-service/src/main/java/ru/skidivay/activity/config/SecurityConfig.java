package ru.skidivay.activity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.skidivay.auth.security.JwtAuthFilter;
import ru.skidivay.auth.security.JwtService;


@Configuration
public class SecurityConfig {
  @Bean
  JwtService jwtService(@Value("${jwt.secret}") String secret,
                        @Value("${jwt.accessTtlSeconds}") long accessTtlSeconds,
                        @Value("${jwt.refreshTtlSeconds}") long refreshTtlSeconds) {
    return new JwtService(secret, accessTtlSeconds, refreshTtlSeconds);
  }

  @Bean
  SecurityFilterChain chain(HttpSecurity http, JwtService jwt) throws Exception {
    http.csrf(csrf->csrf.disable())
      .addFilterBefore(new JwtAuthFilter(jwt), UsernamePasswordAuthenticationFilter.class)
      .authorizeHttpRequests(reg->reg
        .requestMatchers("/actuator/**","/v3/api-docs/**","/swagger-ui/**").permitAll()
        .anyRequest().authenticated());
    return http.build();
  }
}
