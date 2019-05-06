package com.study.demo.mapper;

import com.study.demo.bean.Permission;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PermissionMapperTest {

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    public void findPermissionList() {
        List<Permission> permissionList = permissionMapper.findPermissionList();
        Assert.assertNotSame(0, permissionList.size());
    }

    @Test
    public void findPermissionById() {
        Permission permission = permissionMapper.findPermissionById("1111");
        Assert.assertNotNull(permission);
    }

    @Test
    public void findPermissionByRoleId() {
        List<Permission> permissionList = permissionMapper.findPermissionByRoleId("111");
        Assert.assertNotSame(0, permissionList.size());
    }

    @Test
    public void createPermission() {
        Permission permission = new Permission();
        permission.setId("2222");
        permission.setName("根据用户ID查看用户信息");
        permission.setUrl("user::findUserById");
        Integer ret = permissionMapper.createPermission(permission);
        Assert.assertEquals(ret, Integer.valueOf(1));
    }

    @Test
    public void updatePermission() {
        Permission permission = new Permission();
        permission.setId("1111");
        permission.setName("查看所有用户信息");
        permission.setUrl("user::findUserList");
        Integer ret = permissionMapper.updatePermission(permission);
        Assert.assertEquals(ret, Integer.valueOf(1));
    }

    @Test
    public void deletePermission() {
        Integer ret = permissionMapper.deletePermission("3333");
        Assert.assertEquals(ret, Integer.valueOf(1));
    }
}