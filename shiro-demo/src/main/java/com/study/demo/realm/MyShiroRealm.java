package com.study.demo.realm;

import com.study.demo.bean.Role;
import com.study.demo.bean.User;
import com.study.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:03
 * @Description: 自定义Realm
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private UserService userService;

    /**
     * 用于获取登录成功后的角色、权限等信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Role> roleList = userService.findUserRoleByUsername(user.getUsername());
            Set<String> roleStringSet = new HashSet<String>();
            for (Role role : roleList) {
                roleStringSet.add(role.getRoleName());
            }
            info.setRoles(roleStringSet);

            Set<String> permissionUrlSet = userService.findUserStringPermissionByUsername(user.getUsername());
            info.setStringPermissions(permissionUrlSet);
            return info;
        }
        return null;
    }

    /**
     * 验证当前登录的Subject
     *
     * @param token
     * @return
     * @throws UnknownAccountException
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws UnknownAccountException, AuthenticationException {
        String username = (String) token.getPrincipal();
        //检查token的信息
        User user = userService.findUserByUsername(username);
        if (user == null) {
            log.error("shiro认证用户失败，用户不存在，{}", username);
            throw new UnknownAccountException();
        }
        String password = new String((char[]) token.getCredentials());
        if (!user.getPassword().equals(password)) {
            log.error("shiro认证用户失败，密码错误，{}:{}", username, password);
            throw new AuthenticationException();
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), username);
        return info;
    }
}
