package com.example.oauth.auth.miniapp;

import com.example.oauth.auth.AbstractAuthenticationFilter;
import com.example.oauth.auth.miniapp.token.MiniAppEncryptInfo;
import com.example.oauth.auth.miniapp.token.MiniAppToken;
import com.example.oauth.url.AuthUrl;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by Jackson at 2019/3/14 10:00
 **/
public class MiniAppAuthFilter extends AbstractAuthenticationFilter {

    public MiniAppAuthFilter() {
        super(new AntPathRequestMatcher(AuthUrl.MINI_APP_LOGIN.getUrl(), HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        checkIsPostMethod(request);
        return this.getAuthenticationManager().authenticate(
                new MiniAppToken(getLoginInfo(request, MiniAppEncryptInfo.class)));
    }
}
