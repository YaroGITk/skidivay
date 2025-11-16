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
    private final RefreshTokenService refreshTokenService;
    private final String botToken;

    public TelegramAuthService(
            JwtService jwtService,
            UserService userService,
            RefreshTokenService refreshTokenService,
            @Value("${telegram.botToken}") String botToken
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
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

        String userId = user.getTgId();

        String accessToken = jwtService.issueAccess(
                userId,
                Map.of(
                        "username", user.getTgUsername() == null ? "" : user.getTgUsername(),
                        "plan", user.getPlan()
                )
        );

        String refreshToken = refreshTokenService.create(userId);

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refresh(String refreshToken) {
        var rt = refreshTokenService.validate(refreshToken);
        String userId = rt.getUserId();

        User user = userService.getById(userId); // нужно добавить метод в UserService

        String newAccess = jwtService.issueAccess(
                userId,
                Map.of(
                        "username", user.getTgUsername() == null ? "" : user.getTgUsername(),
                        "plan", user.getPlan()
                )
        );

        // по желанию: ротировать refresh-токен
        String newRefresh = refreshTokenService.create(userId);
        refreshTokenService.revoke(refreshToken);

        return new TokenResponse(newAccess, newRefresh);
    }

    public void logout(String refreshToken) {
        // можно revoke или delete
        refreshTokenService.revoke(refreshToken);
        // refreshTokenService.delete(refreshToken);
    }
}