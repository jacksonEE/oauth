package com.example.oauth.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by Jackson at 2019/1/10 15:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    private Long userId;

    private String loginType;

    private String source;
}
