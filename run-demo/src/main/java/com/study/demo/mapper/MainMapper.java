package com.study.demo.mapper;

import com.study.demo.bean.User;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/2/4 14:33
 * @Description: 整合mybatis,使用的Mapper接口类
 */
public interface MainMapper {

    List<User> getUserList();
}
