package com.ikutarian.mmall.util;

import com.ikutarian.mmall.config.SecurityConfig;
import org.springframework.util.DigestUtils;

public class MD5Utils {

    /**
     * 返回大写的MD5
     */
    public static String encode(String origin) {
        String strWithSalt = origin + SecurityConfig.getInstance().getMd5Salt();
        return DigestUtils.md5DigestAsHex(strWithSalt.getBytes()).toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(encode("123"));
    }
}
