package com.example.oauth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.example.oauth.auth.miniapp.token.MiniAppEncryptInfo;
import com.example.oauth.http.RestResp;
import com.example.oauth.util.JwtKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * created by Jackson at 2019/2/12 16:00
 **/
@Slf4j
@RestController
public class AuthController {

    @Autowired
    private WxMaService wxMaService;

    @Data
    public static class AuthCode {
        @NotBlank(message = "code不能为空")
        private String code;
    }

    @Data
    public static class OpenidLogin {
        private String openid;
    }

    @Data
    public static class LoginSuc {
        public String token;
        public Long expireIn;
    }

    @PostMapping("/oauth/user/mini_app/code")
    public RestResp login(@RequestBody @Valid AuthCode loginUserInfo) throws Exception {
        /*WxMaUserService wxUserService = wxMaService.getUserService();
        WxMaJscode2SessionResult sessionInfo;
        try {
            sessionInfo = wxUserService.getSessionInfo(loginUserInfo.getCode());
            log.info("获取小程序session: " + sessionInfo.toString());
        } catch (Exception e) {
            log.error("获取小程序session失败:" + e.getMessage(), e);
            return RestResp.ok(null);
        }*/
        // 保存session key 到 redis
        // ...
        OpenidLogin openidLogin = new OpenidLogin();
        openidLogin.setOpenid("openid");
        //openidLogin.setOpenid(sessionInfo.getOpenid());
        return RestResp.ok(openidLogin);
    }

    @PostMapping("/oauth/user/mini_app/user_phone")
    public RestResp getWxBindPhone(@RequestBody MiniAppEncryptInfo wxMaEncryptInfo) throws Exception {
        /*String openid = wxMaEncryptInfo.getOpenid();
        String sessionKey = "get sessionKey from redis";
        WxMaUserService wxMaUserService = wxMaService.getUserService();
        WxMaPhoneNumberInfo phoneNoInfo;
        try {
            phoneNoInfo = wxMaUserService.getPhoneNoInfo(sessionKey,
                    wxMaEncryptInfo.getEncryptedData(), wxMaEncryptInfo.getIv());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }*/
        // save user phone info
        return RestResp.ok("获取用户手机信息成功");
    }

    @PostMapping("/login")
    public RestResp login() {
        return RestResp.ok(getLoginSuc());
    }

    @PostMapping("/test")
    public RestResp testLogin() {
        return RestResp.ok("测试成功");
    }

    @PostMapping("/oauth/ex")
    public RestResp tt(HttpServletRequest request) {
        Exception ex = (Exception) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        return RestResp.ok("登录失败:" + ex.getMessage());
    }

    private LoginSuc getLoginSuc() {
        LoginSuc loginSuc = new LoginSuc();
        Object userDetail = SecurityContextHolder.getContext().getAuthentication().getDetails();
        loginSuc.setToken(JwtKit.sign(userDetail, 86400));
        loginSuc.setExpireIn(86400L);
        return loginSuc;
    }
}
