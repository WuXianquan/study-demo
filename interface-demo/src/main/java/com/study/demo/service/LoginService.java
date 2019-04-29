package com.study.demo.service;

import com.study.demo.result.ResponseBean;

/**
 * @Author: Lon
 * @Date: 2019/4/28 15:01
 * @Description: 用户登录登出接口
 */
public interface LoginService {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    ResponseBean login(String username, String password);

    /**
     * 登出
     * @param username
     * @param password
     * @return
     */
    ResponseBean logout(String username, String password);
}
