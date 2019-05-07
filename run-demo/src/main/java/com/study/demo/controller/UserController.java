package com.study.demo.controller;

import com.study.demo.bean.User;
import com.study.demo.result.ResponseBean;
import com.study.demo.service.UserService;
import com.study.demo.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:46
 * @Description:
 */
@Api(tags = "用户API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userList")
    @ApiOperation(value = "用户列表")
    @RequiresPermissions("user:findUserList")
    public ResponseBean userList() {
        List<User> userList = userService.findUserList();
        return ResponseUtil.successResponse(userList);
    }

    @GetMapping("/info")
    @ApiOperation(value = "单个用户信息")
    @RequiresPermissions("user:info")
    public ResponseBean info(@RequestParam("id") Long id) {
        User user = userService.findUserById(id);
        return ResponseUtil.successResponse(user);
    }

    @PostMapping("create")
    @ApiOperation(value = "新建用户")
    @RequiresPermissions("user:create")
    public ResponseBean create(@Valid @RequestBody User user) {
        userService.createUser(user);
        return ResponseUtil.successResponse(user);
    }

    @PostMapping("update")
    @ApiOperation(value = "修改用户")
    @RequiresPermissions("user:update")
    public ResponseBean update(@Valid @RequestBody User user) {
        userService.updateUser(user);
        return ResponseUtil.successResponse(user);
    }
}
