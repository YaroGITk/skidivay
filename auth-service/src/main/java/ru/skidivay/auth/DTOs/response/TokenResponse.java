package ru.skidivay.auth.DTOs.response;

public class TokenResponse {

    public String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
