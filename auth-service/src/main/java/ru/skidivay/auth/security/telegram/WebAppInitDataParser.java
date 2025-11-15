package ru.skidivay.auth.security.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Парсер и валидатор Telegram Mini App initData.
 * Проверяет подпись (HMAC-SHA256 по алгоритму Telegram) и, при желании, "срок годности" по auth_date.
 */
public final class WebAppInitDataParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private WebAppInitDataParser() {}

    /**
     * Разобрать и провалидировать initData.
     *
     * @param initDataRaw  строка из window.Telegram.WebApp.initData (передавать как есть)
     * @param botToken     токен бота (для расчёта секрета)
     * @param maxAge       максимальный "возраст" initData (напр. Duration.ofHours(1)); null — без проверки
     * @return WebAppInitData (типизированные поля user/chat/receiver и пр.)
     * @throws IllegalArgumentException если подпись не сходится, формат неверный или истёк срок годности
     */
    public static WebAppInitData parseAndValidate(String initDataRaw, String botToken, Duration maxAge) {
        if (initDataRaw == null || initDataRaw.isEmpty()) {
            throw new IllegalArgumentException("initData is empty");
        }

        System.out.println("initDataRaw: " + initDataRaw);
        if (botToken == null || botToken.isEmpty()) {
            throw new IllegalArgumentException("botToken is empty");
        }

        Map<String, String> params = parseQueryString(initDataRaw);

        String hash = params.remove("hash");
        if (hash == null || hash.isEmpty()) {
            throw new IllegalArgumentException("hash param is missing");
        }

        // data_check_string: отсортированные по ключу пары "k=v", исключая hash, соединённые \n
        String dataCheckString = buildDataCheckString(params);

        // 1) secret = HMAC_SHA256(key="WebAppData", message=botToken)
        // 2) calc = HMAC_SHA256(key=secret, message=data_check_string) -> hex
        String calculated = hmacHex(hmacBytes("WebAppData".getBytes(StandardCharsets.UTF_8),
                        botToken.getBytes(StandardCharsets.UTF_8)),
                dataCheckString.getBytes(StandardCharsets.UTF_8));

        if (!calculated.equalsIgnoreCase(hash)) {
            throw new IllegalArgumentException("Invalid initData signature");
        }

        // Проверка "срока годности" по auth_date (если задан maxAge)
        if (maxAge != null) {
            String authDateStr = params.get("auth_date");
            if (authDateStr == null) {
                throw new IllegalArgumentException("auth_date is missing for lifetime check");
            }
            long authDateSec;
            try {
                authDateSec = Long.parseLong(authDateStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("auth_date is not a number");
            }
            Instant authInstant = Instant.ofEpochSecond(authDateSec);
            if (authInstant.plus(maxAge).isBefore(Instant.now())) {
                throw new IllegalArgumentException("initData is expired");
            }
        }

        return toModel(params, hash);
    }

    // ---- helpers ----

    private static Map<String, String> parseQueryString(String qs) {
        Map<String, String> out = new LinkedHashMap<>();
        for (String part : qs.split("&")) {
            if (part.isEmpty()) continue;
            int eq = part.indexOf('=');
            String k, v;
            if (eq < 0) {
                k = urlDecode(part);
                v = "";
            } else {
                k = urlDecode(part.substring(0, eq));
                v = urlDecode(part.substring(eq + 1));
            }
            out.put(k, v);
        }
        return out;
    }

    private static String urlDecode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }

    private static String buildDataCheckString(Map<String, String> paramsWithoutHash) {
        List<String> pairs = new ArrayList<>();
        for (Map.Entry<String, String> e : paramsWithoutHash.entrySet()) {
            pairs.add(e.getKey() + "=" + e.getValue());
        }
        pairs.sort(Comparator.naturalOrder());
        return String.join("\n", pairs);
    }

    private static byte[] hmacBytes(byte[] key, byte[] message) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(message);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("HmacSHA256 not available", e);
        }
    }

    private static String hmacHex(byte[] key, byte[] message) {
        byte[] bytes = hmacBytes(key, message);
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private static WebAppInitData toModel(Map<String, String> p, String hash) {
        WebAppInitData m = new WebAppInitData();
        m.setHash(hash);
        m.setQueryId(p.get("query_id"));
        m.setAuthDate(parseLongOrNull(p.get("auth_date")));
        m.setChatType(p.get("chat_type"));
        m.setChatInstance(p.get("chat_instance"));
        m.setStartParam(p.get("start_param"));
        m.setCanSendAfter(parseLongOrNull(p.get("can_send_after")));

        m.setUser(parseJsonOrNull(p.get("user"), WebAppUser.class));
        m.setReceiver(parseJsonOrNull(p.get("receiver"), WebAppUser.class));
        m.setChat(parseJsonOrNull(p.get("chat"), WebAppChat.class));

        // Доп. “сырые” поля (если понадобятся)
        m.setRawParams(Collections.unmodifiableMap(new LinkedHashMap<>(p)));

        return m;
    }

    private static Long parseLongOrNull(String s) {
        if (s == null) return null;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static <T> T parseJsonOrNull(String json, Class<T> type) {
        if (json == null) return null;
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse JSON for " + type.getSimpleName(), e);
        }
    }
}

