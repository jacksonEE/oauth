package com.example.oauth.url;

import lombok.Getter;

/**
 * created by Jackson at 2019/3/15 11:10
 **/
@Getter
public enum AuthUrl {

    MINI_APP_LOGIN("/oauth/user/mini_app/login", "微信小程序登录"),
    WX_CODE_LOGIN("/oauth/user/wx/login", "app端微信授权登录"),
    MANAGER_LOGIN("/oauth/manager/login", "后台管理员登录");
    // PC端授权，短信验证码..

    private String url;
    private String desc;

    AuthUrl(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    public static boolean contains(String url) {
        for (AuthUrl oauthUrl : values()) {
            if (oauthUrl.url.equals(url)) {
                return true;
            }
        }
        return false;
    }
}
