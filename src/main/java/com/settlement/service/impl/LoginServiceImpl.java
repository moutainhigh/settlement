package com.settlement.service.impl;

import com.settlement.service.LoginService;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.HttpStateEnum;
import com.settlement.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * @description 登陆验证
 *
 * @author admin
 * @date 2019/11/08.
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Override
    public Result login(String userName, String password) {
        Result r = null;
        if (StringUtils.isBlank(userName)) {
            System.out.println("in");
            r = new Result(HttpResultEnum.LOGIN_CODE_500.getCode(), HttpResultEnum.LOGIN_CODE_500.getMessage());
            return r;
        }
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            currentUser.login(token);// 传到MyAuthorizingRealm类中的方法进行认证
            Session session = currentUser.getSession();
            session.setAttribute("userName", userName);
            return new Result(HttpResultEnum.CODE_200.getCode(), HttpResultEnum.CODE_200.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            r = new Result(HttpStateEnum.UNAUTHORIZED.getIndex(),HttpStateEnum.UNAUTHORIZED.getName());
        }
        return r;
    }
}
