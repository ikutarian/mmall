package com.ikutarian.mmall.common;

public enum ResponseCode {

    SUCCESS(0, "success"),
    ERROR(-1, "error"),

    NEED_LOGIN(10, "未登录"),
    ILLEGAL_ARGUMENT(2, "参数不合法"),
    USER_NOT_EXIST(10000, "用户不存在"),
    PASSWORD_INVALID(10001, "密码错误");

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
