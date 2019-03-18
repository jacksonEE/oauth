package com.example.oauth.auth.miniapp.token;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * created by Jackson at 2019/3/14 10:12
 **/
@Data
public class MiniAppEncryptInfo {

    @NotBlank(message = "openid不能为空")
    private String openid;

    @NotBlank(message = "encryptedData不能为空")
    private String encryptedData;

    @NotBlank(message = "iv不能为空")
    private String iv;

}
