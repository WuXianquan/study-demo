package com.study.demo.enums;

import lombok.Getter;

/**
 * @Author: Lon
 * @Date: 2019/4/28 16:08
 * @Description: 用户服务异常枚举类
 */
@Getter
public enum UserErrorEnum {

    USER_NOTEXITS("401", "用户不存在"),
    USER_FALSEPASSWORD("402", "账号与密码不匹配"),
    USER_HASLOGINED("403", "账号已登录"),
    USER_USERNAMEUSED("404", "用户名已被占用");

    private String errorCode;

    private String errorMsg;

    UserErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
