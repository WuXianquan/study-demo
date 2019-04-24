package com.study.demo.mapper;

import com.study.demo.bean.User;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:24
 * @Description: 用户Mapper接口类
 */
public interface UserMapper {

    List<User> getUserList();

    User findUserById(String id);

    User findUserByUsername(String username);
}
