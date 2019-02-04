package com.study.demo.service.impl;

import com.study.demo.RunApplication;
import com.study.demo.service.MainService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Auther: Lon
 * @Date: 2019/2/4 16:18
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunApplication.class)
public class MainServiceImplTest {

    @Autowired
    private MainService mainService;

    @Test
    public void setRedis() {
        mainService.setRedis("name", "Mark");
    }

    @Test
    public void isExitRedisKey() {
        Assert.assertTrue(mainService.isExitRedisKey("name"));
    }

    @Test
    public void getRedis() {
        Assert.assertEquals("Mark",  mainService.getRedis("name"));
    }

    @Test
    public void getUserList() {
        Assert.assertNotNull(mainService.getUserList());
    }
}