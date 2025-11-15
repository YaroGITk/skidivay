package ru.skidivay.auth.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain http(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable())
       .authorizeHttpRequests(reg->reg
           .requestMatchers("/auth/**","/actuator/**","/v3/api-docs/**","/swagger-ui/**", "/**").permitAll()
           .anyRequest().authenticated());
    return http.build();
  }
}
