package com.study.demo.mapper;

import com.study.demo.bean.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/5/6 16:50
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void findRoleList() {
        List<Role> roleList =  roleMapper.findRoleList();
        Assert.assertNotSame(0, roleList);
    }

    @Test
    public void findRoleById() {
        Role role = roleMapper.findRoleById("111");
        Assert.assertNotNull(role);
    }

    @Test
    public void findRoleListByUsername() {
        List<Role> roleList = roleMapper.findRoleListByUsername("x");
        Assert.assertNotSame(0, roleList);
    }

    @Test
    public void createRole() {
        Role role = new Role();
        role.setId("999");
        role.setIsDel(0);
        role.setRoleName("测试角色");
        Integer ret = roleMapper.createRole(role);
        Assert.assertEquals(ret, Integer.valueOf(1));
    }

    @Test
    public void updateRole() {
        Role role = new Role();
        role.setId("999");
        role.setRoleName("测试");
        Integer ret = roleMapper.updateRole(role);
        Assert.assertEquals(ret, Integer.valueOf(1));
    }

    @Test
    public void deleteRole() {
        Integer ret = roleMapper.deleteRole("999");
        Assert.assertEquals(ret, Integer.valueOf(1));
    }
}