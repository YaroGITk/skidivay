package ru.skidivay.auth.security.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebAppUser {
    private Long id;
    private String first_name;
    private String last_name;
    private String username;
    private String language_code;
    private Boolean is_premium;
    private Boolean is_bot;
    private Boolean allows_write_to_pm;
    private String photo_url;

    public Long getId() { return id; }
    public String getFirstName() { return first_name; }
    public String getLastName() { return last_name; }
    public String getUsername() { return username; }
    public String getLanguageCode() { return language_code; }
    public Boolean getIsPremium() { return is_premium;}
    public Boolean getIsBot() { return is_bot; }
    public Boolean getAllowsWriteToPm() { return allows_write_to_pm; }
    public String getPhotoUrl() { return photo_url; }

    public void setId(Long id) { this.id = id; }
    public void setFirstName(String first_name) { this.first_name = first_name; }
    public void setLastName(String last_name) { this.last_name = last_name; }
    public void setUsername(String username) { this.username = username; }
    public void setLanguageCode(String language_code) { this.language_code = language_code; }
    public void setIsPremium(Boolean is_premium) { this.is_premium = is_premium; }
    public void setIsBot(Boolean is_bot) { this.is_bot = is_bot; }
    public void setAllowsWriteToPm(Boolean allows_write_to_pm) { this.allows_write_to_pm = allows_write_to_pm; }
    public void setPhotoUrl(String photo_url) { this.photo_url = photo_url; }
}
