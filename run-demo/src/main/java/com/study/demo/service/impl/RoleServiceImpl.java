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
        return roleMapper.findRoleList();
    }

    @Override
    public Role findRoleById(String id) {
        return roleMapper.findRoleById(id);
    }

    @Override
    public List<Role> findRoleListByUsername(String username) {
        return roleMapper.findRoleListByUsername(username);
    }

    @Override
    public Integer createRole(Role role) {
        return roleMapper.createRole(role);
    }

    @Override
    public Integer updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public Integer deleteRole(String id) {
        return roleMapper.deleteRole(id);
    }
}
