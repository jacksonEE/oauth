package com.example.oauth.auth.miniapp.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * created by Jackson at 2019/1/24 15:44
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxUserInfo {

    private String openid;

    private String nickname;

    private Integer sex;

    private String province;

    private String city;

    private String country;

    private String headimgurl;

    private List<String> privilege;

    private String unionid;
}
