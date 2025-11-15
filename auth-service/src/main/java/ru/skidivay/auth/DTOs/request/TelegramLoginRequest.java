package ru.skidivay.auth.DTOs.request;


import jakarta.validation.constraints.NotBlank;

public class TelegramLoginRequest {

    @NotBlank
    String initData;

    public TelegramLoginRequest(@NotBlank String initData) {
        this.initData = initData;
    }

    public String getInitData() { return initData; }

}
