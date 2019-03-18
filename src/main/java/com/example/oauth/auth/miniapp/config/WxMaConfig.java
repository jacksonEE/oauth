package com.example.oauth.auth.miniapp.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jackson on 2018/9/29 13:37
 */
@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfig {

    private WxMaProperties properties;

    @Autowired
    public WxMaConfig(WxMaProperties properties) {
        this.properties = properties;
    }

    @Bean
    public WxMaService services() {
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(properties.getMiniAppid());
        config.setSecret(properties.getMiniSecret());
        config.setToken(properties.getToken());
        config.setAesKey(properties.getAesKey());
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }

}
