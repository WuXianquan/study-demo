package com.study.demo.mapper;

import com.study.demo.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: Lon
 * @Date: 2019/5/6 17:44
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findUserList() {
        List<User> userList = userMapper.findUserList();
        Assert.assertNotNull(userList);
    }
}