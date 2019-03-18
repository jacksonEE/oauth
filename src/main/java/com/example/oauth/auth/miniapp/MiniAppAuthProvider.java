package com.example.oauth.auth.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.example.oauth.auth.UserDetails;
import com.example.oauth.auth.miniapp.token.MiniAppEncryptInfo;
import com.example.oauth.auth.miniapp.token.MiniAppToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * created by Jackson at 2019/1/10 10:07
 **/
@Slf4j
public class MiniAppAuthProvider implements AuthenticationProvider {

    private WxMaService wxMaService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(MiniAppToken.class, authentication,
                () -> "MiniAppAuthProvider.onlySupports Only MiniAppToken is supported");
        /*if (authentication != null) {
            MiniAppToken token = (MiniAppToken) authentication;
            MiniAppEncryptInfo miniAppLogin = token.getMiniAppLogin();
            WxMaUserService wxMaUserService = wxMaService.getUserService();
            String sessionKey = "abc";
            *//*if (sessionKey == null) {
                throw new BadCredentialsException("请重新授权");
            }*//*
            WxMaUserInfo wxMaUserInfo;
            try {
                wxMaUserInfo = wxMaUserService.getUserInfo(sessionKey,
                        miniAppLogin.getEncryptedData(), miniAppLogin.getIv());
            } catch (Exception e) {
                log.error("小程序解密信息失败:" + e.getMessage(), e);
                throw new BadCredentialsException("获取用户信息失败");
            }
            // 保存用户信息
            ((MiniAppToken) authentication).setDetails(new UserDetails(1L, "USER", "MINI_APP"));
            authentication.setAuthenticated(true);
        }*/
        ((MiniAppToken) authentication).setDetails(new UserDetails(1L, "USER", "MINI_APP"));
        authentication.setAuthenticated(true);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (MiniAppToken.class.isAssignableFrom(authentication));
    }

    public void setWxMaService(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }
}
