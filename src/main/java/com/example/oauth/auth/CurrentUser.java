package com.example.oauth.auth;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * created by Jackson at 2019/1/10 11:51
 **/
public class CurrentUser {

    /**
     * 单体应用下使用当前登录信息
     * @return UserDetails
     */
    public static UserDetails userDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    //

}
