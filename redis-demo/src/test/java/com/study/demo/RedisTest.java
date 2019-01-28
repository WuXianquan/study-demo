package com.study.demo;

import com.study.demo.Util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: Lon
 * @Date: 2019/1/28 17:58
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void set(){
        redisUtil.set("name", "Mark");
    }

    @Test
    public void get(){
        redisUtil.get("name");
    }
}
