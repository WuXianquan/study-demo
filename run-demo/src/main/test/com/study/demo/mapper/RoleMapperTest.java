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
        Assert.assertNotNull(roleList);
    }
}