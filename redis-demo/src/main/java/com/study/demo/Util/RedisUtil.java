package com.study.demo.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: Lon
 * @Date: 2019/1/28 17:33
 * @Description: Redis工具类
 */
@Component()
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 默认有效期数
     */
    private static final long DEFAULT_LIVETIME = 7;

    /**
     * 默认有效期单位
     */
    private static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.DAYS;

    /**
     * 存值，默认生存时间7天
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, DEFAULT_LIVETIME, DEFAULT_TIMEUNIT);
    }

    /**
     * 存值
     *
     * @param key      键
     * @param value    值
     * @param liveTime 生存时间
     * @param timeUnit 生存单位
     */
    public void set(String key, Object value, long liveTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, liveTime, timeUnit);
    }

    /**
     * 取值
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 键-值 是否存在
     *
     * @param key 键
     * @return
     */
    public Boolean isExist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除 键-值
     *
     * @param key
     * @return
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
