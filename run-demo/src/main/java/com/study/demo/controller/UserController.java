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
 * @Date: 2019/4/23 16:46
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    @ResponseBody
    public ResponseBean info(@RequestParam("id") String id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return  ResponseUtil.failResponse(UserErrorEnum.USER_NOTEXITS.getErrorCode(), UserErrorEnum.USER_NOTEXITS.getErrorMsg());
        }
        return ResponseUtil.successResponse(user);
    }
}
