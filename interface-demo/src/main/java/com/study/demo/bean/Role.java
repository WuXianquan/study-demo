package com.study.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/30 17:18
 * @Description: 角色实体类
 */
@Data
@JsonIgnoreProperties(value={"isDel"})
public class Role implements Serializable {

    private String id;

    private String roleName;

    private Integer isDel;

    private List<Permission> permissions;
}
