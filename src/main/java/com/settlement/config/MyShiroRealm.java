package com.settlement.config;

import com.settlement.entity.SysPermission;
import com.settlement.entity.SysPermissionRole;
import com.settlement.entity.SysRole;
import com.settlement.entity.SysUser;
import com.settlement.service.SysPermissionRoleService;
import com.settlement.service.SysRoleService;
import com.settlement.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description shiro realm
 * Created by Administrator on 2019/11/10.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionRoleService sysPermissionRoleService;

    /**
     * 设置realm name
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName("MyShiroRealm");
    }

    /**
     *@description 权限验证:根据认证数据获取用户的的权限信息
     *
     * @param principalCollection 包含了所有的已经认证的安全数据
     *        authorizationInfo 授权数据
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取已经认证的用户数据
        SysUser user = (SysUser)principalCollection.getPrimaryPrincipal();
        //查询用户的角色
        SysRole sysRole = sysRoleService.findRoleByUserId(user.getId());
        //查询用户的权限信息
        List<SysPermission> sysPermissions = sysPermissionRoleService.getPermissionByRoleId(sysRole.getId());
        //存储所有权限code
        List<String> permissions = new ArrayList<>();
        for(SysPermission sysPermission : sysPermissions) {
            permissions.add(sysPermission.getPermission());
        }
        //构造返回数据
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //设置权限集合
        authorizationInfo.addStringPermissions(permissions);
        //设置角色集全
        authorizationInfo.addRole(sysRole.getRoleCode());
        return authorizationInfo;
    }


    /**
     * @description 用户身份验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       //获取登录用户信息(用户名和密码)
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userName = token.getUsername(); //(String)authenticationToken.getPrincipal();
        String password = new String(token.getPassword());
        // 根据用户名从数据库取得用户信息
        SysUser user = sysUserService.findUserByEmail(userName);
        if (user == null && user.getPassword().equals(password)) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                user.getPassword(), //密码
                // ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
