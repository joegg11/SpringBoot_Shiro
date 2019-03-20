package com.joe.shiro;

/*import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;*/
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 *@Author joe zhou
 *@Description shiro的配置类
 *@Date 22:19 2019/3/20
 *@Param
 *@return
 **/
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /**
         * Shiro内置过滤器，实现相关权限的拦截
         * anon:无需认证 登陆就可以访问
         * authc:必须认证才能访问
         * user:使用rememberme的功能才能直接访问
         * perms:该资源必须得到资源权限才能访问
         * role:才资源必须得到角色权限才能访问
         */

        Map<String, String> filterMap = new LinkedHashMap<String, String>();

        /*filterMap.put("/add", "authc");
        filterMap.put("/update", "authc");*/

        filterMap.put("/testThymeleaf", "anon");

        filterMap.put("/login", "anon");

        //授权过滤器
        //perms[] 括号里的内容貌似可以随便写 但是要与Realm里的授权逻辑中的资源授权字符串相同
        filterMap.put("/add", "perms[aaa]");
        filterMap.put("/update", "perms[bbb]");

        filterMap.put("/*", "authc");

        shiroFilterFactoryBean.setLoginUrl("/toLogin");

        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSercurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /**
     * 配置ShiroDialect 用于thymeleaf和shiro标签配合使用
     */
   /* @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }*/
}
