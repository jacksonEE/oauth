package com.example.oauth.auth.app.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

/**
 * created by Jackson at 2019/3/15 14:34
 **/
public class WxAppToken extends AbstractAuthenticationToken {

    private String code;

    /**
     * Creates a token with the supplied array of authorities.
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public WxAppToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public WxAppToken(String code) {
        this(new ArrayList<>());
        this.code = code;
    }

    @Override
    public Object getCredentials() {
        return code;
    }

    @Override
    public Object getPrincipal() {
        return code;
    }
}
