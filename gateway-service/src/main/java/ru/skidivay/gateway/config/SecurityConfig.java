package ru.skidivay.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
      .authorizeExchange(reg -> reg
        .pathMatchers("/auth/**","/actuator/**","/swagger-ui/**","/v3/api-docs/**", "/**").permitAll()
        .anyExchange().permitAll())
      .httpBasic(Customizer.withDefaults())
      .build();
  }
}
