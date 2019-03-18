package com.example.oauth.auth.miniapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "platform.wx.ma")
public class WxMaProperties {

    /**
     * 设置微信小程序的appid
     */
    private String miniAppid;

    /**
     * 设置微信小程序的Secret
     */
    private String miniSecret;

    /**
     * 设置微信小程序消息服务器配置的token
     */
    private String token;

    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    private String aesKey;

    /**
     * 消息格式，XML或者JSON
     */
    private String msgDataFormat;

    public String getMiniAppid() {
        return miniAppid;
    }

    public void setMiniAppid(String miniAppid) {
        this.miniAppid = miniAppid;
    }

    public String getMiniSecret() {
        return miniSecret;
    }

    public void setMiniSecret(String miniSecret) {
        this.miniSecret = miniSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getMsgDataFormat() {
        return msgDataFormat;
    }

    public void setMsgDataFormat(String msgDataFormat) {
        this.msgDataFormat = msgDataFormat;
    }
}
