package com.example.oauth.auth.app.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by Jackson at 2019/1/24 15:38
 **/
@Data
@NoArgsConstructor
public class WxResult {

    private String access_token;
    private int expires_in;
    private String openid;
    private String scope;
    private String unionid;
    private String refresh_token;
}
