package com.example.oauth.auth.app;

import com.example.oauth.auth.UserDetails;
import com.example.oauth.auth.app.config.WxResult;
import com.example.oauth.auth.app.config.WxService;
import com.example.oauth.auth.app.token.WxAppToken;
import com.example.oauth.auth.miniapp.config.WxUserInfo;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * created by Jackson at 2019/3/15 14:41
 **/
@Slf4j
public class WxAppAuthProvider implements AuthenticationProvider {

    private WxService wxService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(WxAppToken.class, authentication,
                () -> "WxAppAuthProvider.onlySupports Only WxAppToken is supported");
        /*WxAppToken wxAppToken = (WxAppToken) authentication;
        WxResult wxResult;
        try {
            wxResult = wxService.getAccessToken(wxAppToken.getPrincipal().toString());
        } catch (WxErrorException e) {
            log.error("授权失败:" + e.getMessage(), e);
            throw new BadCredentialsException("授权失败:" + e.getMessage());
        }

        try {
            WxUserInfo userInfo = wxService.getUserInfo(wxResult);
        } catch (WxErrorException e) {
            throw new BadCredentialsException("获取用户信息出错:" + e.getMessage());
        }
*/
        // save user info
        authentication.setAuthenticated(true);
        ((WxAppToken) authentication).setDetails(new UserDetails(1L, "USER", "WX_APP"));
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WxAppToken.class.isAssignableFrom(authentication);
    }

    public void setWxService(WxService wxService) {
        this.wxService = wxService;
    }
}
