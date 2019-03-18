package com.example.oauth.auth.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

/**
 * created by Jackson at 2019/3/14 15:35
 **/
public class JwtToken extends AbstractAuthenticationToken {

    private String token;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public JwtToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public JwtToken(String token) {
        this(new ArrayList<>());
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
