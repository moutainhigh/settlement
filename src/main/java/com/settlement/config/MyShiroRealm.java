package com.settlement.config;

import com.settlement.entity.SysPermission;
import com.settlement.entity.SysRole;
import com.settlement.entity.SysUser;
import com.settlement.service.SysRoleService;
import com.settlement.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

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

    /**
     *@description 权限验证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser user = (SysUser)principalCollection.getPrimaryPrincipal();
        List<SysRole> roleList = sysRoleService.findRoleByUserId(user.getId());
        // user.setRoles(roleList);
        /*for(SysRole role:user.getRoles()){
            authorizationInfo.addRole(role.getRoleCnName());
            for(SysPermission p:role.getPermissions()){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }*/
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
        String userName = (String)authenticationToken.getPrincipal();
        String pwd = new String((char[]) authenticationToken.getCredentials());
        // 根据用户名从数据库取得用户信息
        SysUser user = sysUserService.findUserByEmail(userName);
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getEmail(), //这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                user.getPassword(), //密码
                // ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
