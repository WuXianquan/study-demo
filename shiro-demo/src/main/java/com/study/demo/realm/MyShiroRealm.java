package  com.study.demo.realm;

import com.study.demo.bean.User;
import com.study.demo.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:03
 * @Description:
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 用于获取登录成功后的角色、权限等信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 验证当前登录的Subject
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        //检查token的信息
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), username);
        return info;
    }
}
