package com.ikutarian.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {

    private static final Logger log = LoggerFactory.getLogger(TokenCache.class);

    private static final LoadingCache<String, String> LOCAL_CACHE = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                // 默认的数据加载实现，当调用get取值时，如果没有key对应的value，就调用这个方法进行加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void set(String key, String value) {
        LOCAL_CACHE.put(key, value);
    }

    public static String get(String key) {
        try {
            String value = LOCAL_CACHE.get(key);
            if ("null".equals(value)) {
                return null;
            } else {
                return value;
            }
        } catch (Exception e) {
            log.error("localCache get error", e);
            return null;
        }
    }

    public static void remove(String key) {
    }
}
