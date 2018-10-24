package com.ikutarian.mmall.common;

public class Const {

    /**
     * Session常量定义
     */
    public static final class Session {
        /**
         * 当前用户的id
         */
        public static final String CURRENT_USER_ID = "currentUser_id";
    }

    /**
     * 角色
     */
    public static final class Role {
        /**
         * 普通用户
         */
        public static final int CUSTOMER = 0;
        /**
         * 管理员
         */
        public static final int ADMIN = 1;
    }

    /**
     * 字段类型
     */
    public static final class Type {
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";
        public static final String PHONE = "phone";
    }

    /**
     * Token的相关常量
     */
    public static final class Token {
        public static final String FORGET_PASSWORD = "token_";
    }

    /**
     * 商品分类
     */
    public static final class Category {
        public static final String ROOT_CATEGORY_ID = "0";
    }
}
