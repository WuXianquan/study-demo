package com.study.demo.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Lon
 * @Date: 2019/5/7 15:34
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void set() {
        redisUtil.set("redisSetTestKey", "redisSetTestValue");
    }

    @Test
    public void set1() {
        redisUtil.set("redisSetLiveTestKey", "redisSetLiveTestValue", 5, TimeUnit.SECONDS);
    }

    @Test
    public void get() {
        Object value = redisUtil.get("redisSetTestKey");
        Assert.assertEquals("redisSetTestValue", value);
    }

    @Test
    public void isExist() {
        boolean exist = redisUtil.isExist("redisSetTestKey");
        Assert.assertTrue(exist);
    }

    @Test
    public void delete() {
        boolean deleteSuccess = redisUtil.delete("redisSetTestKey");
        Assert.assertTrue(deleteSuccess);
    }
}