package com.study.demo.mapper;

import com.study.demo.bean.Role;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/30 17:47
 * @Description: 角色Mapper接口类
 */
public interface RoleMapper {

    /**
     * 获取所有角色列表
     * @return
     */
    List<Role> findRoleList();

    /**
     * 根据Id获取角色信息
     * @param id
     * @return
     */
    Role findRoleById(String id);

    /**
     * 根据用户名称获取角色信息
     * @param username
     * @return
     */
    List<Role> findRoleListByUsername(String username);

    /**
     * 添加角色
     * @param role
     * @return
     */
    Integer createRole(Role role);

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    Integer updateRole(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Integer deleteRole(String id);
}
