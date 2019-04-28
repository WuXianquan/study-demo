package com.study.demo.result;

import lombok.Data;

/**
 * @Author: Lon
 * @Date: 2019/4/24 17:35
 * @Description: restful风格请求返回参数
 */
@Data
public class ResponseBean {

    // 状态码
    private String code;

    // 返回信息
    private String msg;

    // 返回的数据
    private Object data;

    public ResponseBean() {
    }

    public ResponseBean(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public ResponseBean(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
