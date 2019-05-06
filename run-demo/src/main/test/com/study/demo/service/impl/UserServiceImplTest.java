package com.study.demo.service.impl;

import com.study.demo.bean.Role;
import com.study.demo.bean.User;
import com.study.demo.mapper.UserMapper;
import com.study.demo.service.UserService;
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
 * @Date: 2019/5/6 17:50
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void findUserList() {
        List<User> userList = userService.findUserList();
        Assert.assertNotNull(userList);
    }

    @Test
    public void findUserById() {
    }

    @Test
    public void findUserByUsername() {
    }

    @Test
    public void createUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void findUserRoleByUsername() {
        List<Role> roleList = userService.findUserRoleByUsername("x");
        Assert.assertNotNull(roleList);
    }

    @Test
    public void findUserPermissionByUsername() {
    }

    @Test
    public void findUserStringPermissionByUsername() {
    }
}