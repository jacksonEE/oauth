package com.example.oauth.auth;

import com.example.oauth.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * created by Jackson at 2019/3/15 13:36
 **/
@Slf4j
public abstract class AbstractAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected AbstractAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected void checkIsPostMethod(HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
    }

    protected <T> T getLoginInfo(HttpServletRequest request, Class<T> cls) {
        BufferedReader reader;
        try {
            reader = request.getReader();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
        StringBuilder sb = new StringBuilder();
        reader.lines().forEach(sb::append);
        return JsonUtil.readValue(sb.toString(), cls);
    }
}
