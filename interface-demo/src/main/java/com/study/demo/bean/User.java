package com.study.demo.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
/**
 * @Author: Lon
 * @Date: 2019/4/16 14:11
 * @Description: 用户实体类
 */
@Data
public class User implements Serializable {

    private BigInteger id;

    private String username;

    private String password;

    private String createTime;

    private String updateTime;

    private List<Role> roles;
}
