package com.study.demo.handler;

import com.study.demo.constant.CommonConstant;
import com.study.demo.exception.ServiceException;
import com.study.demo.result.ResponseBean;
import com.study.demo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Lon
 * @Date: 2019/5/7 14:53
 * @Description: 服务异常处理类
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseBean handleRuntimeException(HttpServletRequest request, RuntimeException re) {
        log.error("请求{}异常:{}", request.getServletPath(), re.getMessage(), re);
        return ResponseUtil.defaultFailResponse();
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseBean handleServiceException(HttpServletRequest request, ServiceException se) {
        log.error("请求{}异常:{}", request.getServletPath(), se.getMessage(), se);
        return ResponseUtil.failResponse(se.getCode(), se.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handleShiroException(HttpServletRequest request, UnauthorizedException ue) {
        log.error("请求{}异常:{}", request.getServletPath(), ue.getMessage(), ue);
        return ResponseUtil.failResponse(CommonConstant.NOAUTH_CODE, CommonConstant.NOAUTH_MSG);
    }
}
