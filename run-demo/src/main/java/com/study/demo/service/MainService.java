package com.study.demo.service;

import com.study.demo.bean.*;

import java.util.List;

public interface MainService {

    void setRedis(String key, Object value);

    Boolean isExitRedisKey(String key);

    Object getRedis(String key);

    List<User> getUserList();
}
