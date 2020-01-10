package com.settlement.service.impl;

import com.settlement.service.LoginService;
import com.settlement.service.SysUserService;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.HttpStateEnum;
import com.settlement.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 登陆验证
 *
 * @author admin
 * @date 2019/11/08.
 */
@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result login(String userName, String password) {
        Result r = null;
        if (StringUtils.isBlank(userName)) {
            r = new Result(HttpResultEnum.LOGIN_CODE_500.getCode(), HttpResultEnum.LOGIN_CODE_500.getMessage());
            return r;
        }
        //密码加密
        String password2=new Md5Hash(password,userName,3).toString();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);// 传到MyAuthorizingRealm类中的方法进行认证
            Session session = currentUser.getSession();
            session.setAttribute("user", sysUserService.findUserByEmail(userName));
            r = new Result(HttpResultEnum.CODE_200.getCode(), HttpResultEnum.CODE_200.getMessage());
        } catch (UnknownAccountException e) {
            // e.printStackTrace();
            r = new Result(HttpResultEnum.LOGIN_CODE_501.getCode(), HttpResultEnum.LOGIN_CODE_501.getMessage());
        }  catch (IncorrectCredentialsException e) {
            r = new Result(HttpResultEnum.LOGIN_CODE_502.getCode(), HttpResultEnum.LOGIN_CODE_502.getMessage());
        }   catch (AuthenticationException e) {
            r= new Result(HttpResultEnum.LOGIN_CODE_500.getCode(), HttpResultEnum.LOGIN_CODE_500.getMessage());
        }
        // Session session = currentUser.getSession();
            // session.setAttribute("userName", userName);
       /* }catch (Exception e) {
            e.printStackTrace();
            r = new Result(HttpStateEnum.UNAUTHORIZED.getIndex(),HttpStateEnum.UNAUTHORIZED.getName());
        }*/
        return r;
    }
}
