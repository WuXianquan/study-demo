package com.study.demo.exception;

import com.study.demo.constant.CommonConstant;

/**
 * @Author: Lon
 * @Date: 2019/5/7 14:43
 * @Description: 服务实现层异常类
 */
public class ServiceException extends BaseException {

    public ServiceException() {
        this.code = CommonConstant.FAIL_CODE;
        this.msg = CommonConstant.FAIL_MSG;
    }

    public ServiceException(String code, String msg) {
        super.code = code;
        super.msg = msg;
    }
}
