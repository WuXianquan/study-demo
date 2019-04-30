package com.study.demo.mapper;

import com.study.demo.bean.User;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:24
 * @Description: 用户Mapper接口类
 */
public interface UserMapper {

    /**
     * 获取所有用户列表
     * @return
     */
    List<User> findUserList();

    /**
     * 根据Id获取用户信息
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 添加用户
     * @param user
     * @return
     */
    Integer createUser(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    Integer updateUser(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Integer deleteUser(String id);
}
