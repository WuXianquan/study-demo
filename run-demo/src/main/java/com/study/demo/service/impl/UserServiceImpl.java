package com.study.demo.service.impl;

import com.study.demo.bean.User;
import com.study.demo.mapper.UserMapper;
import com.study.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:23
 * @Description: 基础用户实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public User findUserById(String id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
