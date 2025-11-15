package ru.skidivay.hunted.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.skidivay.common.security.JwtService;
import ru.skidivay.common.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {
  @Bean
  JwtService jwtService(@Value("${jwt.secret}") String secret,
                        @Value("${jwt.ttlSeconds}") long ttl) {
    return new JwtService(secret, ttl);
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
