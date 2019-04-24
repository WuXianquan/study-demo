package com.study.demo.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author: Lon
 * @Date: 2019/4/17 14:18
 * @Description:  http请求工具类
 */
public class HttpUtil {

    /**
     * 同步post请求
     * @param url
     * @param param
     * @return
     */
    public static JSONObject synchroPost (String url, Map param) {
        RestTemplate restTemplate = new RestTemplate();
        JSONObject result =  restTemplate.getForObject(url, JSONObject.class, param);
        return result;
    }
}
