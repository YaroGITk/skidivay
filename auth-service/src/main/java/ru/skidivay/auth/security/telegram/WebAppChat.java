package ru.skidivay.auth.security.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebAppChat {
    public Long id;
    public String type;
    public String title;
    public String username;
    public String photo_url;
}