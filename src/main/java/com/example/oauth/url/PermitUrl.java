package com.example.oauth.url;

import java.util.HashSet;
import java.util.Set;

/**
 * created by Jackson at 2019/3/15 14:01
 **/
public class PermitUrl {

    // 小程序授权码换取授权码
    private static final String MINI_APP_AUTH_CODE_URL = "/oauth/user/mini_app/code";

    private static final Set<String> PERMIT_URL = new HashSet<>();

    static {
        PERMIT_URL.add(MINI_APP_AUTH_CODE_URL);
    }

    public static String[] allToArray() {
        return PERMIT_URL.toArray(new String[0]);
    }

    public static boolean contains(String url) {
        return PERMIT_URL.contains(url);
    }
}
