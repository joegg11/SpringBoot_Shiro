package com.joe.shiro;

import com.joe.domain.User;
import com.joe.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/*
 *@Author joe zhou
 *@Description 自定义Realm
 *@Date 22:14 2019/3/20
 *@Param
 *@return
 **/
public class UserRealm extends AuthorizingRealm {
    /**
     * 执行授权逻辑
      * @param principalCollection
     * @return
     */

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        /*info.addStringPermission("1111");*/
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();

        User dbUser = userService.findById(user.getId());

        /*info.addStringPermission(dbUser.getPerms());*/
        info.addStringPermission(dbUser.getPerms());

        return info;
    }


    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

    /*    String name = "joe";
        String password = "123456";*/

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        User user = userService.findByName(token.getUsername());
        if (user==null){
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }

}
