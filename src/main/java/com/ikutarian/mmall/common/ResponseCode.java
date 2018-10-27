package com.ikutarian.mmall.common;

public enum ResponseCode {

    // 通用的错误码
    SUCCESS(0, "success"),
    SERVER_ERROR(1, "服务端异常"),
    ILLEGAL_ARGUMENT(2, "参数不合法"),
    VALUE_CONFLICT(3, "校验失败，存在重名字段"),
    INVALID_ROLE(4, "用户角色不合法"),

    // 登录
    USER_NOT_EXIST(10000, "用户不存在"),
    PASSWORD_INVALID(10001, "密码错误"),
    USERNAME_CONFLICT(10002, "用户名已存在"),
    EMAIL_CONFLICT(10003, "email已存在"),
    PHONE_CONFLICT(10004, "手机号已存在"),
    REGISTER_FAIL(10005, "注册失败"),
    NEED_LOGIN(10006, "用户未登录"),
    QUESTION_NOT_EXIST(10007, "密码找回问题是空的"),
    ANSWER_NOT_CORRECT(10008, "密码找回问题答案错误"),
    INVALID_FORGET_PASSWORD_TOKEN(10009, "token无效或过期"),
    RESET_PASSWORD_FAIL(10010, "修改密码失败"),
    INVALID_OLD_PASSWORD(10011, "旧密码错误"),
    UPDATE_INFORMATION_FAIL(10012, "更新个人信息失败"),
    LOGIN_SHOULD_BE_ADMIN(10013, "不是管理员，无法登陆"),

    ADD_CATEGORY_FAILURE(20000, "添加商品分类失败"),
    UPDATE_CATEGORY_FAILURE(20001, "更新商品分类失败"),
    UPDATE_PRODUCT_FAILURE(20003, "更新商品失败"),
    INSERT_PRODUCT_FAILURE(20004, "新增产品失败"),
    UPDATE_PRODUCT_STATUS_FAILURE(20005, "修改产品销售状态失败"),
    PRODUCT_NOT_EXISTS(20006, "商品不存在"),
    PRODUCT_NOT_ON_SALE(20007, "商品已下架"),

    UPLOAD_IMG_FILE_FAIL(30000, "图片上传失败");

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
