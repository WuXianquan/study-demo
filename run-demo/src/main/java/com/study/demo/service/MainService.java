package com.study.demo.service;

public interface MainService {

    void setRedis(String key, Object value);

    Boolean isExitRedisKey(String key);

    Object getRedis(String key);
}
