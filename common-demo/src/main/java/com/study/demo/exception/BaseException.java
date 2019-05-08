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

    protected String message;

    public BaseException() {
        super();
        this.message = CommonConstant.FAIL_MSG;
    }

    public BaseException(String message) {
        this.code = CommonConstant.FAIL_CODE;
        this.message = message;
    }

    public BaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
