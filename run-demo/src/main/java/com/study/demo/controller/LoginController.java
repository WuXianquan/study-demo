package com.study.demo.controller;

import com.study.demo.bean.User;
import com.study.demo.result.ResponseBean;
import com.study.demo.service.LoginService;
import com.study.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private UserService userService;

    @GetMapping("/unauth")
    public String unauth() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/user/index";
        }
        return "/user/login";
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public ResponseBean login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return loginService.login(username, password);
    }

    @PostMapping("/auth/logout")
    @ResponseBody
    public ResponseBean logout() {
        return loginService.logout();
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("username", user.getUsername());
        request.setAttribute("head", userService.findUserHeadByUserId(user.getId()));
        return "/user/index";
    }
}
