package com.study.demo.bean;

import lombok.Data;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/30 17:18
 * @Description: 角色实体类
 */
@Data
public class Role {

    private String id;

    private String roleName;

    private List<Permission> permissions;
}
