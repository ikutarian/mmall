package com.ikutarian.mmall.util;

import org.apache.commons.lang3.StringUtils;

public class MybatisUtils {

    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(StringUtils.trim(str));
    }
}
