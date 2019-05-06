package com.study.demo.service.impl;

import com.study.demo.bean.Permission;
import com.study.demo.bean.Role;
import com.study.demo.bean.User;
import com.study.demo.mapper.UserMapper;
import com.study.demo.service.PermissionService;
import com.study.demo.service.RoleService;
import com.study.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:23
 * @Description: 基础用户实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<User> findUserList() {
        return userMapper.findUserList();
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public Integer createUser(User user) {
        return userMapper.createUser(user);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public Integer deleteUser(Long id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public List<Role> findUserRoleByUsername(String username) {
        return roleService.findRoleListByUsername(username);
    }

    @Override
    public Set<Permission> findUserPermissionByUsername(String username) {
        Set<Permission> permissionSet = new HashSet<>();
        List<Role> roleList = this.findUserRoleByUsername(username);
        for (Role role : roleList) {
            List<Permission> permissionList = permissionService.findPermissionByRoleId(role.getId());
            for (Permission permission : permissionList) {
                permissionSet.add(permission);
            }
        }
        return permissionSet;
    }

    @Override
    public Set<String> findUserStringPermissionByUsername(String username) {
        Set<String> permissionSet = new HashSet<>();
        List<Role> roleList = this.findUserRoleByUsername(username);
        for (Role role : roleList) {
            List<Permission> permissionList = permissionService.findPermissionByRoleId(role.getId());
            for (Permission permission : permissionList) {
                permissionSet.add(permission.getUrl());
            }
        }
        return permissionSet;
    }
}
