package com.study.demo.service.impl;

import com.study.demo.bean.Role;
import com.study.demo.mapper.RoleMapper;
import com.study.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/30 17:44
 * @Description: 角色实现类
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRoleList() {
        return null;
    }

    @Override
    public Role findRoleById(String id) {
        return null;
    }

    @Override
    public List<Role> findRoleListByUsername(String username) {
        List<Role> roleList = roleMapper.findRoleListByUsername(username);
        return roleList;
    }

    @Override
    public Integer createRole(Role role) {
        return null;
    }

    @Override
    public Integer updateRole(Role role) {
        return null;
    }

    @Override
    public Integer deleteRole(String id) {
        return null;
    }
}
