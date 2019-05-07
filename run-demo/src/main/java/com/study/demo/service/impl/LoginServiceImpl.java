package com.study.demo.service.impl;

import com.study.demo.constant.CommonConstant;
import com.study.demo.enums.UserErrorEnum;
import com.study.demo.exception.ServiceException;
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
    public ResponseBean login(String username, String password) {
        ResponseBean rb = ResponseUtil.successResponse(null);

        //创建subject实例
        Subject subject = SecurityUtils.getSubject();
        //判断当前的subject是否登录
        if (subject.isAuthenticated() == true) {
            throw new ServiceException(UserErrorEnum.USER_HASLOGINED.getErrorCode(), UserErrorEnum.USER_HASLOGINED.getErrorMsg());
        } else {
            //将用户名和密码存入UsernamePasswordToken中
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                //将存有用户名和密码的token存进subject中
                subject.login(token);
            } catch (UnknownAccountException uae) {
                throw new ServiceException(UserErrorEnum.USER_NOTEXITS.getErrorCode(), UserErrorEnum.USER_NOTEXITS.getErrorMsg());
            } catch (AuthenticationException ae) {
                throw new ServiceException(UserErrorEnum.USER_FALSEPASSWORD.getErrorCode(), UserErrorEnum.USER_FALSEPASSWORD.getErrorMsg());
            } catch (Exception e) {
                log.error("登录异常，{}", e.getMessage(), e);
                throw new ServiceException();
            }
        }
        return rb;
    }

    @Override
    public ResponseBean logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() == false) {
            throw new ServiceException(CommonConstant.UNAUTH_CODE, CommonConstant.UNAUTH_CODE);
        } else {
            subject.logout();
            return ResponseUtil.successResponse(null);
        }
    }
}
