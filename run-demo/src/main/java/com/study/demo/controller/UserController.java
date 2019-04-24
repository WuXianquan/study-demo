package com.study.demo.controller;

import com.study.demo.bean.User;
import com.study.demo.result.ResponseBean;
import com.study.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:46
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/unauth")
    @ResponseBody
    public ResponseBean unauth() {
        Map<String, Object> map = new HashMap<>();
        return new ResponseBean(9998, "未登录");
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public ResponseBean login(@RequestParam("username") String username, @RequestParam("password") String password) {
        ResponseBean responseBean = new ResponseBean(0000, "登录成功");

        //创建subject实例
        Subject subject = SecurityUtils.getSubject();
        //判断当前的subject是否登录
        if (subject.isAuthenticated() == false) {
            //将用户名和密码存入UsernamePasswordToken中
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                //将存有用户名和密码的token存进subject中
                subject.login(token);
            } catch (UnknownAccountException uae) {
                responseBean.setCode(0001);
                responseBean.setMsg("用户不存在");
            } catch (IncorrectCredentialsException ice) {
                responseBean.setCode(0002);
                responseBean.setMsg("密码错误");
            } catch (LockedAccountException lae) {
                responseBean.setCode(0003);
                responseBean.setMsg("用户已冻结");
            } catch (AuthenticationException e) {
                responseBean.setCode(9999);
                responseBean.setMsg("系统开小差");
            }
        }
        return responseBean;
    }

    @GetMapping("/info")
    @ResponseBody
    public ResponseBean info(@RequestParam("id") String id) {
        User user = userService.findUserById(id);
        return new ResponseBean(0001, "成功", user);
    }
}
