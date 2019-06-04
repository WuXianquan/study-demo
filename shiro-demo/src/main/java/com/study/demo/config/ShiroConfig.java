package com.study.demo.config;

import com.study.demo.realm.MyShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Lon
 * @Date: 2019/4/23 16:01
 * @Description: shiro配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 负责shiroBean的生命周期
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 这是个自定义的认证类，继承子AuthorizingRealm，负责用户的认证和权限处理
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public MyShiroRealm shiroRealm() {
        return new MyShiroRealm();
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置不会被拦截的连接  这里顺序判断
        //anon：所有的url都可以匿名访问
        //authc：所有url都必须认证通过才可以访问
        //user：配置记住我或者认证通过才能访问
        //logout：退出登录
        filterChainDefinitionMap.put("/jq/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        //配置退出过滤器
        filterChainDefinitionMap.put("/user/unauth", "anon");
        filterChainDefinitionMap.put("/user/auth/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/user/noAuth", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        //过滤连接自定义，从上往下顺序执行，所以用LinkHashMap /**放在最下边
        filterChainDefinitionMap.put("/**", "authc");
        //设置登录界面，如果不设置为寻找web根目录下的文件
        shiroFilterFactoryBean.setLoginUrl("/user/unauth");
        //设置登录成功后要跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/user/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
