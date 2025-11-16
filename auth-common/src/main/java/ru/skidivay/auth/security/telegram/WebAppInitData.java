package ru.skidivay.auth.security.telegram;

import java.util.Map;

public class WebAppInitData {
    private String hash;
    private String queryId;
    private Long authDate;
    private Long canSendAfter;
    private String chatType;
    private String chatInstance;
    private String startParam;

    private WebAppUser user;
    private WebAppUser receiver;
    private WebAppChat chat;

    private Map<String, String> rawParams;

    public void setHash(String hash) { this.hash = hash; }
    public void setQueryId(String queryId) { this.queryId = queryId; }
    public void setAuthDate(Long authDate) { this.authDate = authDate; }
    public void setCanSendAfter(Long canSendAfter) { this.canSendAfter = canSendAfter; }
    public void setChatType(String chatType) { this.chatType = chatType; }
    public void setChatInstance(String chatInstance) { this.chatInstance = chatInstance; }
    public void setStartParam(String startParam) { this.startParam = startParam; }
    public void setUser(WebAppUser user) { this.user = user; }
    public void setReceiver(WebAppUser receiver) { this.receiver = receiver; }
    public void setChat(WebAppChat chat) { this.chat = chat; }
    public void setRawParams(Map<String, String> rawParams) { this.rawParams = rawParams; }
    public String getHash() { return hash; }
    public String getQueryId() { return queryId; }
    public Long getAuthDate() { return authDate; }
    public Long getCanSendAfter() { return canSendAfter; }
    public String getChatType() { return chatType; }
    public String getChatInstance() { return chatInstance; }
    public String getStartParam() { return startParam; }
    public WebAppUser getUser() { return user; }
    public WebAppUser getReceiver() { return receiver; }
    public WebAppChat getChat() { return chat; }
    public Map<String, String> getRawParams() { return rawParams; }
}