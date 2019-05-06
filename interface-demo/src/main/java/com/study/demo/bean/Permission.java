package com.study.demo.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lon
 * @Date: 2019/4/30 17:21
 * @Description: 权限实体类
 */
@Data
public class Permission implements Serializable {

    private String id;

    private String url;

    private String name;
}
