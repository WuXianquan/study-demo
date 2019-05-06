package com.study.demo.service.impl;

import com.study.demo.bean.Permission;
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
import java.util.Set;

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
        User user = userService.findUserById(Long.valueOf(1));
        Assert.assertNotNull(user);
    }

    @Test
    public void findUserByUsername() {
        User user = userService.findUserByUsername("x");
        Assert.assertNotNull(user);
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
        Assert.assertNotSame(0, roleList.size());
    }

    @Test
    public void findUserPermissionByUsername() {
        Set<Permission> permissionSet = userService.findUserPermissionByUsername("x");
        Assert.assertNotSame(0, permissionSet.size());
    }

    @Test
    public void findUserStringPermissionByUsername() {
        Set<String> permissionSet = userService.findUserStringPermissionByUsername("x");
        Assert.assertNotSame(0, permissionSet.size());
    }
}