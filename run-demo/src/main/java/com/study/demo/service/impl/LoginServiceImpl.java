package com.study.demo.service.impl;

import com.study.demo.constant.CommonConstant;
import com.study.demo.enums.UserErrorEnum;
import com.study.demo.result.ResponseBean;
import com.study.demo.service.LoginService;
import com.study.demo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * @Author: Lon
 * @Date: 2019/4/28 15:20
 * @Description:
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public ResponseBean unauth() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(CommonConstant.UNAUTH_CODE);
        responseBean.setMsg(CommonConstant.UNAUTH_MSG);
        return responseBean;
    }

    @Override
    public ResponseBean login(String username, String password) {
        ResponseBean rb = ResponseUtil.successResponse(null);

        //创建subject实例
        Subject subject = SecurityUtils.getSubject();
        //判断当前的subject是否登录
        if (subject.isAuthenticated() == true) {
            rb.setCode(UserErrorEnum.USER_HASLOGINED.getErrorCode());
            rb.setMsg(UserErrorEnum.USER_HASLOGINED.getErrorMsg());
        } else{
            //将用户名和密码存入UsernamePasswordToken中
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                //将存有用户名和密码的token存进subject中
                subject.login(token);
            } catch (UnknownAccountException uae) {
                rb.setCode(UserErrorEnum.USER_NOTEXITS.getErrorCode());
                rb.setMsg(UserErrorEnum.USER_NOTEXITS.getErrorMsg());
            } catch (AuthenticationException ae) {
                rb.setCode(UserErrorEnum.USER_FALSEPASSWORD.getErrorCode());
                rb.setMsg(UserErrorEnum.USER_FALSEPASSWORD.getErrorMsg());
            } catch (Exception e) {
                log.error("登录异常，{}", e.getMessage(), e);
                rb = ResponseUtil.defaultFailResponse();
            }
        }
        return rb;
    }

    @Override
    public ResponseBean logout(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() == false) {
            ResponseUtil.failResponse("用户未登录");
        } else {
            subject.logout();
        }
        return ResponseUtil.successResponse(subject.getPrincipal());
    }
}
