package ru.skidivay.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r.path("/auth/**").uri("http://auth-service:8081"))
                .route("users", r -> r.path("/users/**").uri("http://users-service:8082"))
                .route("details", r -> r.path("/details/**").uri("http://details-service:8083"))
                .route("invoices", r -> r.path("/invoices/**").uri("http://invoices-service:8084"))
                .route("activity", r -> r.path("/activity/**").uri("http://activity-service:8085"))
                .route("sessions", r -> r.path("/sessions/**").uri("http://sessions-service:8086"))
                .build();
    }
}
