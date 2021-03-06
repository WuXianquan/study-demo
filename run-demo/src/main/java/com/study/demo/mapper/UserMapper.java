package com.study.demo.mapper;

import com.study.demo.bean.Role;
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
    User findUserById(Long id);

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
    Integer deleteUser(Long id);

    /**
     * 添加用户角色
     * @param list
     * @return
     */
    Integer createUserRoles(List list);

    /**
     * 删除用户角色
     * @param userId 用户ID
     * @return
     */
    Integer deleteUserRoles(Long userId);
}
