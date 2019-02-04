package com.study.demo.mapper;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Lon
 * @Date: 2019/2/4 14:33
 * @Description: 整合mybatis,使用的Mapper接口类
 */
public interface MainMapper {

    List<Map<String, Object>> getUserList();
}
