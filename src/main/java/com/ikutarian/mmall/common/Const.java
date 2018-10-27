package com.ikutarian.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

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

    /**
     * properties配置文件的常量
     */
    public static final class Props {
        /**
         * 文件名
         */
        public static final String FILE_NAME = "mmall.properties";

        /**
         * 图片服务器的域名
         */
        public static final String IMG_SERVER_HOST = "img.host";

        /**
         * 图片文件存放的根路径
         */
        public static final String IMG_BASE_PATH = "img.basePath";

        /**
         * FTP的IP
         */
        public static final String FTP_IP = "ftp.ip";
        /**
         * FTP的端口
         */
        public static final String FTP_PORT = "ftp.port";
        /**
         * FTP的用户名
         */
        public static final String FTP_USERNAME = "ftp.user";
        /**
         * FTP的密码
         */
        public static final String FTP_PASSWORD = "ftp.password";
    }

    /**
     * 分页的配置
     */
    public static final class Page {
        /**
         * 默认起始页
         */
        public static final String DEFAULT_PAGE_NUM = "1";
        /**
         * 默认每一页的数据量
         */
        public static final String DEFAULT_PAGE_SIZE = "10";
    }

    /**
     * 商品的状态
     */
    public static final class ProductStatus {
        /**
         * 上架
         */
        public static final int ON_SALE = 1;
    }

    public static final class ProducutListOrderBy {
        public static final Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }
}
