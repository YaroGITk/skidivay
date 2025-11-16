package ru.skidivay.auth.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.skidivay.auth.DTOs.request.RefreshTokenRequest;
import ru.skidivay.auth.DTOs.request.TelegramLoginRequest;
import ru.skidivay.auth.DTOs.response.TokenResponse;
import ru.skidivay.auth.services.TelegramAuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {
  private final TelegramAuthService telegramAuthService;

  public AuthController(TelegramAuthService telegramAuthService) {
    this.telegramAuthService = telegramAuthService;
  }

  @PostMapping("/telegram")
  public TokenResponse telegram(@RequestBody @Valid TelegramLoginRequest req) {
    return telegramAuthService.authenticate(req);
  }

  @PostMapping("/refresh")
  public TokenResponse refresh(@RequestBody @Valid RefreshTokenRequest req) {
    return telegramAuthService.refresh(req.getRefreshToken());
  }

  @PostMapping("/logout")
  public void logout(@RequestBody @Valid RefreshTokenRequest req) {
    telegramAuthService.logout(req.getRefreshToken());
  }
}
