package com.study.demo.bean;

import lombok.Data;

import java.util.List;
/**
 * @Author: Lon
 * @Date: 2019/4/16 14:11
 * @Description: 用户实体类
 */
@Data
public class User {

    private String id;

    private String username;

    private String password;

    private Data createTime;

    private Data updateTime;

    private List<Role> roles;
}
