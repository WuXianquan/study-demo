package com.study.demo.service;

import com.study.demo.bean.Permission;
import com.study.demo.bean.Role;
import com.study.demo.bean.User;

import java.util.List;
import java.util.Set;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:22
 * @Description: 基础用户接口
 */
public interface UserService {

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

    /**
     * 根据用户名获取用户角色
     * @param username
     */
    List<Role> findUserRoleByUsername(String username);

    /**
     * 根据用户名获取用户权限
     * @param username
     */
    Set<Permission> findUserPermissionByUsername(String username);

    /**
     * 根据用户名获取用户权限
     * @param username
     */
    Set<String> findUserStringPermissionByUsername(String username);
}
