package com.study.demo;

import com.study.demo.util.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: Lon
 * @Date: 2019/1/28 17:58
 * @Description: Redis单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void set() {
        redisUtil.set("name", "Mark");
    }

    @Test
    public void get() {
        Assert.assertEquals(redisUtil.get("name"), "Mark");
    }

    @Test
    public void isExist(){
        Assert.assertEquals(redisUtil.isExist("name"), true);
    }

    @Test
    public void delete(){
        Assert.assertEquals(redisUtil.delete("name"), true);
    }
}
