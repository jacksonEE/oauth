package com.example.oauth.auth.jwt;

import com.example.oauth.auth.UserDetails;
import com.example.oauth.util.JwtKit;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * created by Jackson at 2019/3/14 15:41
 **/
public class JwtAuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtToken token = (JwtToken) authentication;
        UserDetails userDetails = JwtKit.unSign(token.getPrincipal().toString(), UserDetails.class);
        if (userDetails != null && userDetails.getUserId() != null) {
            token.setAuthenticated(true);
            ((JwtToken) authentication).setDetails(userDetails);
        } else {
            throw new BadCredentialsException("authorization 错误");
        }
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtToken.class.isAssignableFrom(authentication);
    }


}
