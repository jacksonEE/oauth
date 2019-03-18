package com.example.oauth.auth.miniapp.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

/**
 * created by Jackson at 2019/1/18 11:34
 **/
public class MiniAppToken extends AbstractAuthenticationToken {


    private MiniAppEncryptInfo miniAppLogin;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    private MiniAppToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public MiniAppToken(MiniAppEncryptInfo miniAppLogin) {
        this(new ArrayList<>());
        this.miniAppLogin = miniAppLogin;
    }

    @Override
    public Object getCredentials() {
        return miniAppLogin.getOpenid();
    }

    @Override
    public Object getPrincipal() {
        return miniAppLogin.getOpenid();
    }

    public MiniAppEncryptInfo getMiniAppLogin() {
        return miniAppLogin;
    }
}
