package com.study.demo.util;

import com.study.demo.constant.CommonConstant;
import com.study.demo.result.ResponseBean;

/**
 * @Author: Lon
 * @Date: 2019/4/28 14:52
 * @Description: service方法返回结果工具类
 */
public class ResponseUtil {

    public static ResponseBean successResponse(Object data) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(CommonConstant.SUCCESS_CODE);
        responseBean.setMsg(CommonConstant.SUCCESS_MSG);
        responseBean.setData(data);
        return responseBean;
    }

    /**
     * 默认请求失败响应体
     * @return
     */
    public static ResponseBean defaultFailResponse() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(CommonConstant.FAIL_CODE);
        responseBean.setMsg(CommonConstant.FAIL_MSG);
        return responseBean;
    }

    public static ResponseBean failResponse(String msg) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(CommonConstant.FAIL_CODE);
        responseBean.setMsg(msg);
        return responseBean;
    }

    public static ResponseBean failResponse(String code, String msg) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(code);
        responseBean.setMsg(msg);
        return responseBean;
    }
}
