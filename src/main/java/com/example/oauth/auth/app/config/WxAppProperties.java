package com.example.oauth.auth.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * created by Jackson at 2019/1/24 10:39
 **/
@Data
@ConfigurationProperties(prefix = "platform.wx.app")
public class WxAppProperties {

    /**
     * 设置微信三方平台的appid
     */
    private String appid;

    /**
     * 设置微信三方平台的app secret
     */
    private String secret;
}
