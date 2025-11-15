package ru.skidivay.auth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skidivay.auth.DTOs.request.TelegramLoginRequest;
import ru.skidivay.auth.DTOs.response.TokenResponse;
import ru.skidivay.auth.models.User;
import ru.skidivay.auth.security.JwtService;
import ru.skidivay.auth.security.telegram.WebAppInitData;
import ru.skidivay.auth.security.telegram.WebAppInitDataParser;
import ru.skidivay.auth.security.telegram.WebAppUser;

import java.time.Duration;
import java.util.Map;

@Service
public class TelegramAuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final String botToken;

    public TelegramAuthService(
            JwtService jwtService,
            UserService userService,
            @Value("${telegram.botToken}") String botToken
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.botToken = botToken;
    }

    public TokenResponse authenticate(TelegramLoginRequest req) {
        WebAppInitData parsed = WebAppInitDataParser.parseAndValidate(
                req.getInitData(),
                botToken,
                Duration.ofHours(1)
        );

        WebAppUser tgUser = parsed.getUser();

        User user = userService.findOrCreateByTelegramUser(tgUser);

        String token = jwtService.issue(
                user.getTgId(),
                Map.of(
                        "username", user.getTgUsername() == null ? "" : user.getTgUsername(),
                        "plan", user.getPlan()
                )
        );

        return new TokenResponse(token);
    }
}