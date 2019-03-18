package com.example.oauth.auth.app.config;

import com.example.oauth.auth.miniapp.config.WxUserInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.http.apache.DefaultApacheHttpClientBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * created by Jackson at 2019/1/24 15:33
 **/
@Component
@Configuration
@EnableConfigurationProperties(WxAppProperties.class)
public class WxService {

    private static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    @Autowired
    private WxAppProperties wxAppProperties;

    private CloseableHttpClient httpClient;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        httpClient = DefaultApacheHttpClientBuilder.get().build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public WxResult getAccessToken(String code) throws WxErrorException {
        String url = String.format(OAUTH2_ACCESS_TOKEN_URL, wxAppProperties.getAppid(),
                wxAppProperties.getSecret(), code);
        return sendRequest(url, WxResult.class, false);
    }

    public WxUserInfo getUserInfo(WxResult wxResult) throws WxErrorException {
        String url = String.format(USER_INFO_URL, wxResult.getAccess_token(),
                wxResult.getOpenid());
        return sendRequest(url, WxUserInfo.class, true);
    }

    private <T> T sendRequest(String url, Class<T> cls, boolean utf) throws WxErrorException {
        try {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String resultContent = new BasicResponseHandler().handleResponse(response);
                validWxError(resultContent);
                String result = utf ? new String(resultContent.getBytes(StandardCharsets.ISO_8859_1),
                        StandardCharsets.UTF_8) : resultContent;
                return objectMapper.readValue(result, cls);
            } finally {
                httpGet.releaseConnection();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validWxError(String resultContent) throws WxErrorException {
        WxError error = WxError.fromJson(resultContent);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }
    }
}
