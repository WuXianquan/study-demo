package com.study.demo.service;

import com.study.demo.bean.Permission;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/30 17:31
 * @Description: 权限接口类
 */
public interface PermissionService {

    /**
     * 获取所有权限列表
     * @return
     */
    List<Permission> findPermissionList();

    /**
     * 根据Id获取权限信息
     * @param id
     * @return
     */
    Permission findPermissionById(String id);

    /**
     * 添加权限
     * @param Permission
     * @return
     */
    Integer createPermission(Permission Permission);

    /**
     * 修改权限信息
     * @param Permission
     * @return
     */
    Integer updatePermission(Permission Permission);

    /**
     * 删除权限
     * @param id
     * @return
     */
    Integer deletePermission(String id);
}