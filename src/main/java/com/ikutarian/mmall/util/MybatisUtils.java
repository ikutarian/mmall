package com.ikutarian.mmall.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class MybatisUtils {

    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(StringUtils.trim(str));
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return CollectionUtils.isNotEmpty(coll);
    }
}
