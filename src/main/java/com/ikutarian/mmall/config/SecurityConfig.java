package com.ikutarian.mmall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 有关安全的配置
 */
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * MD5盐
     */
    private String md5Salt;

    private SecurityConfig() {
        Properties pps = new Properties();
        try {
            pps.load(SecurityConfig.class.getClassLoader().getResourceAsStream("mmall.properties"));
            md5Salt = pps.getProperty("md5.salt", "");
        } catch (IOException e) {
            log.error("SecurityConfig", "配置文件读取失败");
            e.printStackTrace();
        }
    }

    private static class SingletonHolder {
        private static SecurityConfig instance = new SecurityConfig();
    }

    public static SecurityConfig getInstance() {
        return SingletonHolder.instance;
    }

    public String getMd5Salt() {
        return md5Salt;
    }
}