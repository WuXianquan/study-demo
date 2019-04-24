package com.study.demo.service;

import com.study.demo.bean.User;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:22
 * @Description: 基础用户接口
 */
public interface UserService {

    List<User> getUserList();

    User findUserById(String id);

    User findUserByUsername(String username);
}
