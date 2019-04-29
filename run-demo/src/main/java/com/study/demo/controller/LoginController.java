package com.study.demo.controller;

import com.study.demo.bean.User;
import com.study.demo.enums.UserErrorEnum;
import com.study.demo.result.ResponseBean;
import com.study.demo.service.LoginService;
import com.study.demo.service.UserService;
import com.study.demo.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lon
 * @Date: 2019/4/29 11:27
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/unauth")
    public String unauth() {
        return "/user/login";
    }

    @PostMapping("/auth/login")
    public void login(@RequestParam("username") String username, @RequestParam("password") String password) {
        loginService.login(username, password);
//        return "/user/index";
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseBean logout(@RequestParam("username") String username, @RequestParam("password") String password) {
        return loginService.logout(username, password);
    }

    @GetMapping("/index")
    public String index() {
        return "/user/index";
    }

}
