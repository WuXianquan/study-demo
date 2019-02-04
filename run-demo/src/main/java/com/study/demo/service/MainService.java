package com.study.demo.service;

import java.util.List;
import java.util.Map;

public interface MainService {

    void setRedis(String key, Object value);

    Boolean isExitRedisKey(String key);

    Object getRedis(String key);

    List<Map<String, Object>> getUserList();
}
