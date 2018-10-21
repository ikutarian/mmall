package com.ikutarian.mmall.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ServerResponse {

    private int code;
    private String msg;
    private Object data;

    public ServerResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS.getCode();
    }

    public static  ServerResponse createBySuccess(String message, Object data) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), message, data);
    }

    public static ServerResponse createByError(ResponseCode responseCode) {
        return new ServerResponse(responseCode.getCode(), responseCode.getMessage(), null);
    }

    // 这里不添加访问修饰符，默认最多只能同一个包下的类能调用这个方法
    static ServerResponse createByError(int code, String message) {
        return new ServerResponse(code, message, null);
    }
}
