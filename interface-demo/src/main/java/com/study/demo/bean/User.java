package com.study.demo.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * @Author: Lon
 * @Date: 2019/4/16 14:11
 * @Description: 用户实体类
 */
@Data
@ApiModel
public class User implements Serializable {

    @ApiModelProperty(value="用户id",dataType="Long",name="id")
    private Long id;

    @ApiModelProperty(value="用户名",dataType="String",name="username",example="x",required = true)
    private String username;

    @ApiModelProperty(value="登录密码",dataType="String",name="password",example="1",required = true)
    private String password;

    @ApiModelProperty(value="注册时间",dataType="String",name="createTime",readOnly = true)
    private String createTime;

    @ApiModelProperty(value="上次修改时间",dataType="String",name="updateTime")
    private String updateTime;

    @ApiModelProperty(value="角色信息",dataType="List",name="roles")
    private List<Role> roles;
}
