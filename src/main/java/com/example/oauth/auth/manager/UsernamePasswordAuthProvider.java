package com.example.oauth.auth.manager;

import com.example.oauth.auth.UserDetails;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * created by Jackson at 2019/3/18 10:39
 **/
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ((UsernamePasswordAuthenticationToken) authentication)
                .setDetails(new UserDetails(2L,"MANAGER","PC"));
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
