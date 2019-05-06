package com.study.demo.mapper;

import com.study.demo.bean.Permission;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/30 17:49
 * @Description: 权限Mapper接口类
 */
public interface PermissionMapper {

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
     * 根据角色id获取权限信息
     * @param roleId
     * @return
     */
    List<Permission> findPermissionByRoleId(String roleId);

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
