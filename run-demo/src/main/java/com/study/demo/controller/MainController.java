package com.study.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Lon
 * @Date: 2019/1/28 10:21
 * @Description:
 */
@RestController
public class MainController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}
