package com.study.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.demo.service.MainService;
import com.study.demo.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/httpPostTest")
    public Object httpPostTest() {
        Map param = new HashMap();
        param.put("a", 1);
        param.put("b", "test");
        JSONObject jsonObject = HttpUtil.synchroPost("http://localhost:8080/postReturnTest", param);
        return jsonObject;
    }

    @RequestMapping("/postReturnTest")
    public Object postReturnTest(Map param) {
        return param;
    }
}
