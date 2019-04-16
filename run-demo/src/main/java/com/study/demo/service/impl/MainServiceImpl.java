package com.study.demo.service.impl;

import com.study.demo.bean.User;
import com.study.demo.mapper.MainMapper;
import com.study.demo.util.RedisUtil;
import com.study.demo.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MainMapper mainMapper;

    @Override
    public void setRedis(String key, Object value) {
        redisUtil.set(key, value);
    }

    @Override
    public Boolean isExitRedisKey(String key) {
        return redisUtil.isExist(key);
    }

    @Override
    public Object getRedis(String key) {
        return redisUtil.get(key);
    }

    @Override
    public List<User> getUserList() {
        return mainMapper.getUserList();
    }
}
