package com.example.oauth.auth.app;

import com.example.oauth.auth.AbstractAuthenticationFilter;
import com.example.oauth.auth.app.token.WxAppAuthCode;
import com.example.oauth.auth.app.token.WxAppToken;
import com.example.oauth.url.AuthUrl;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created by Jackson at 2019/3/15 14:37
 **/
public class WxAppAuthFilter extends AbstractAuthenticationFilter {

    public WxAppAuthFilter() {
        super(new AntPathRequestMatcher(AuthUrl.WX_CODE_LOGIN.getUrl(), HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        WxAppAuthCode loginInfo = getLoginInfo(request, WxAppAuthCode.class);
        return this.getAuthenticationManager().authenticate(new WxAppToken(loginInfo.getCode()));
    }
}
