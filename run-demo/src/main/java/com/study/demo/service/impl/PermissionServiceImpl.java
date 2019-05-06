package com.study.demo.service.impl;

import com.study.demo.bean.Permission;
import com.study.demo.mapper.PermissionMapper;
import com.study.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/30 17:45
 * @Description: 权限实现类
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermissionList() {
        return permissionMapper.findPermissionList();
    }

    @Override
    public Permission findPermissionById(String id) {
        return permissionMapper.findPermissionById(id);
    }

    @Override
    public List<Permission> findPermissionByRoleId(String roleId) {
        return permissionMapper.findPermissionByRoleId(roleId);
    }

    @Override
    public Integer createPermission(Permission Permission) {
        return permissionMapper.createPermission(Permission);
    }

    @Override
    public Integer updatePermission(Permission Permission) {
        return permissionMapper.updatePermission(Permission);
    }

    @Override
    public Integer deletePermission(String id) {
        return permissionMapper.deletePermission(id);
    }
}
