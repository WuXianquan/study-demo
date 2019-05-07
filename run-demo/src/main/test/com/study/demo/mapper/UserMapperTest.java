package com.study.demo.mapper;

import com.study.demo.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Assert.assertNotSame(0, userList);
    }

    @Test
    public void findUserById() {
        Long id = Long.valueOf(1);
        User user = userMapper.findUserById(id);
        Assert.assertEquals(user.getId(), id);
    }

    @Test
    public void findUserByUsername() {
        User user = userMapper.findUserByUsername("x");
        Assert.assertEquals("x", user.getUsername());
    }

    @Test
    public void createUser() {
        User user = new User();
        user.setUsername("a");
        user.setPassword("1");
        Integer ret = userMapper.createUser(user);
        Assert.assertEquals(ret, Integer.valueOf(1));
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setUsername("x");
        Integer ret = userMapper.updateUser(user);
        Assert.assertEquals(ret, Integer.valueOf(1));
    }

    @Test
    public void deleteUser() {
        Integer ret = userMapper.deleteUser(Long.valueOf(1));
        Assert.assertEquals(ret, Integer.valueOf(1));
    }

    @Test
    public void createUserRoles() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("userId", 1);
        map1.put("roleId", "111");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("userId", 1);
        map2.put("roleId", "222");
        list.add(map1);
        list.add(map2);
        Integer ret = userMapper.createUserRoles(list);
        Assert.assertNotSame(ret, 0);
    }

    @Test
    public void deleteUserRoles() {
        Integer ret = userMapper.deleteUserRoles(Long.valueOf(1));
        Assert.assertNotSame(ret, 0);
    }
}