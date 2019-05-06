package com.study.demo.controller;

import com.study.demo.bean.User;
import com.study.demo.enums.UserErrorEnum;
import com.study.demo.result.ResponseBean;
import com.study.demo.service.UserService;
import com.study.demo.util.ResponseUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:46
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresPermissions("user::FindUserList")
    @GetMapping("/userList")
    public ResponseBean userList() {
        List<User> userList = userService.findUserList();
        return ResponseUtil.successResponse(userList);
    }

    @GetMapping("/info")
    public ResponseBean info(@RequestParam("id") Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return  ResponseUtil.failResponse(UserErrorEnum.USER_NOTEXITS.getErrorCode(), UserErrorEnum.USER_NOTEXITS.getErrorMsg());
        }
        return ResponseUtil.successResponse(user);
    }
}
