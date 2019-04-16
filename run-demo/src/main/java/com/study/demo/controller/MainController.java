package com.study.demo.controller;

import com.study.demo.bean.User;
import com.study.demo.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/1/28 10:21
 * @Description:
 */
@Slf4j
@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
    
    @RequestMapping("/setRedis")
    public Boolean setRedis(@RequestParam("key") String key, @RequestParam("value") Object value) {
        mainService.setRedis(key, value);
        return mainService.isExitRedisKey(key);
    }

    @RequestMapping("/getRedis")
    public Object getRedis(@RequestParam("key") String key) {
        return mainService.getRedis(key);
    }

    @RequestMapping("/getUserList")
    public List<User> getUserList() {
        return mainService.getUserList();
    }
}
