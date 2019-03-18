package com.example.oauth.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * created by Jackson at 2019/2/12 11:02
 **/
public final class JsonUtil {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T readValue(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        T t = null;
        try {
            t = MAPPER.readValue(json, cls);
        } catch (IOException e) {
            // do nothing
        }
        return t;
    }
}
