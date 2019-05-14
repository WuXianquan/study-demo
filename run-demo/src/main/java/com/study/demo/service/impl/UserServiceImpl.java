package com.study.demo.service.impl;

import com.study.demo.bean.Permission;
import com.study.demo.bean.Role;
import com.study.demo.bean.User;
import com.study.demo.enums.UserErrorEnum;
import com.study.demo.exception.ServiceException;
import com.study.demo.mapper.UserMapper;
import com.study.demo.service.PermissionService;
import com.study.demo.service.RoleService;
import com.study.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

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
        User user = userMapper.findUserById(id);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userMapper.findUserByUsername(username);
        return user;
    }

    @Transactional
    @Override
    public Integer createUser(User user) {
        User u = this.findUserByUsername(user.getUsername());
        if (u != null) {
            throw new ServiceException(UserErrorEnum.USER_USERNAMEUSED.getErrorCode(), UserErrorEnum.USER_USERNAMEUSED.getErrorMsg());
        }
        int ret = userMapper.createUser(user);
        if (ret != 1) {
            throw new ServiceException("新增失败");
        }

        List<Map<String, Object>> list = new ArrayList<>();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            Role r = roleService.findRoleById(role.getId());
            if (r == null) {
                throw new ServiceException("角色不存在");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getId());
            map.put("roleId", role.getId());
            list.add(map);
        }
        userMapper.createUserRoles(list);
        return ret;
    }

    @Transactional
    @Override
    public Integer updateUser(User user) {
        Long userId = user.getId();
        User u = this.findUserById(userId);
        if (u == null) {
            throw new ServiceException(UserErrorEnum.USER_NOTEXITS.getErrorCode(), UserErrorEnum.USER_NOTEXITS.getErrorMsg());
        }
        int ret = userMapper.updateUser(user);
        if (ret != 1) {
            throw new ServiceException("修改失败");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            Role r = roleService.findRoleById(role.getId());
            if (r == null) {
                throw new ServiceException("角色不存在");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("roleId", role.getId());
            list.add(map);
        }
        userMapper.deleteUserRoles(userId);
        userMapper.createUserRoles(list);
        return ret;
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
