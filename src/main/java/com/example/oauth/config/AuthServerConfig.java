package com.example.oauth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.example.oauth.auth.jwt.JwtAuthFilter;
import com.example.oauth.auth.jwt.JwtAuthProvider;
import com.example.oauth.auth.manager.UsernamePasswordAuthProvider;
import com.example.oauth.auth.miniapp.MiniAppAuthFilter;
import com.example.oauth.auth.miniapp.MiniAppAuthProvider;
import com.example.oauth.url.PermitUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * created by Jackson at 2019/2/1 15:12
 **/
@Configuration
@EnableWebSecurity
public class AuthServerConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Value("${server.error.path:${error.path:/error}}")
    public String errorPath;

    private final ForwardAuthenticationSuccessHandler globalSuccessHandler =
            new ForwardAuthenticationSuccessHandler("/login");

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    // 自定义过滤器
                //.addFilterBefore(httpServletRequestReplacedFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(miniappAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(usernamePasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                // 配置登陆页/login并允许访问
                .cors()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers(PermitUrl.allToArray())
                .permitAll()
                .filterSecurityInterceptorOncePerRequest(true)
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(miniAppAuthProvider())
                .authenticationProvider(jwtAuthProvider())
                .authenticationProvider(usernamePasswordAuthProvider());
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthFilter() throws Exception {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/oauth/manager/login", HttpMethod.POST.name()));
        filter.setAuthenticationManager(this.authenticationManagerBean());
        filter.setAuthenticationFailureHandler(failureHandler());
        filter.setAuthenticationSuccessHandler(globalSuccessHandler);
        return filter;
    }

    @Bean
    public MiniAppAuthFilter miniappAuthFilter() throws Exception {
        MiniAppAuthFilter miniappAuthFilter = new MiniAppAuthFilter();
        miniappAuthFilter.setAuthenticationManager(this.authenticationManagerBean());
        miniappAuthFilter.setAuthenticationSuccessHandler(globalSuccessHandler);
        return miniappAuthFilter;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() throws Exception {
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter();
        jwtAuthFilter.setAuthenticationManager(this.authenticationManagerBean());
        jwtAuthFilter.setHandlerMapping(requestMappingHandlerMapping);
        jwtAuthFilter.setErrorPath(errorPath);
        jwtAuthFilter.setAuthenticationSuccessHandler((req, res, authentication) -> {
            if (authentication.isAuthenticated()) {
                req.getRequestDispatcher(req.getRequestURI()).forward(req, res);
            }
        });
        jwtAuthFilter.setAuthenticationFailureHandler(failureHandler());
        return jwtAuthFilter;
    }

    @Bean
    public ForwardAuthenticationFailureHandler failureHandler() {
        return new ForwardAuthenticationFailureHandler("/oauth/ex");
    }

    private MiniAppAuthProvider miniAppAuthProvider() {
        MiniAppAuthProvider miniAppAuthProvider = new MiniAppAuthProvider();
        miniAppAuthProvider.setWxMaService(wxMaService);
        return miniAppAuthProvider;
    }

    private JwtAuthProvider jwtAuthProvider() {
        return new JwtAuthProvider();
    }

    private UsernamePasswordAuthProvider usernamePasswordAuthProvider() {
        return new UsernamePasswordAuthProvider();
    }
}
