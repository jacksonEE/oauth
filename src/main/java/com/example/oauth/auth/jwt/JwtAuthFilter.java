package com.example.oauth.auth.jwt;

import com.example.oauth.auth.AbstractAuthenticationFilter;
import com.example.oauth.url.AuthUrl;
import com.example.oauth.url.PermitUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于验证token
 * created by Jackson at 2019/3/14 14:40
 **/
public class JwtAuthFilter extends AbstractAuthenticationFilter {

    private String errorPath;

    private RequestMappingHandlerMapping handlerMapping;

    public JwtAuthFilter() {
        super(new AntPathRequestMatcher("*", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            throw new AuthenticationCredentialsNotFoundException("authorization 不能为空");
        }
        return this.getAuthenticationManager().authenticate(new JwtToken(authorization));
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        if (AuthUrl.contains(requestURI) || PermitUrl.contains(requestURI)) {
            return false;
        } else {
            boolean flag = true;
            if (check404(request)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                flag = false;
            }
            if (requestURI.equalsIgnoreCase(errorPath)) {
                flag = false;
            }
            return flag;
        }
    }

    private boolean check404(HttpServletRequest request) {
        try {
            HandlerExecutionChain handler = handlerMapping.getHandler(request);
            if (handler == null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.handlerMapping = requestMappingHandlerMapping;
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }
}
