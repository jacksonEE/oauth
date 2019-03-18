package com.example.oauth.http;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResp {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public RestResp(Object data) {
        this.data = data;
    }

    public static RestResp ok(Object data) {
        return new RestResp(data);
    }

    public Object getData() {
        return data;
    }
}
