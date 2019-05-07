package com.study.demo.exception;

import com.study.demo.constant.CommonConstant;
import lombok.Data;

/**
 * @Author: Lon
 * @Date: 2019/4/28 16:44
 * @Description: 基础异常类
 */
@Data
public class BaseException extends RuntimeException {

    protected String code;

    protected String msg;

    public BaseException() {
        super();
        this.msg = CommonConstant.FAIL_MSG;
    }

    public BaseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
