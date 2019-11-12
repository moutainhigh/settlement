package com.settlement.controller;

import com.settlement.service.LoginService;
import com.settlement.service.SysUserService;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description Home控制器类
 *
 * @author admin
 * @date 2019/11/07.
 */
@Controller
public class HomeController {

    @Autowired
    private LoginService loginService;

    @GetMapping({"/","/login"})
    public String toLogin() {
        return "login";
    }

    @GetMapping("/home")
    public String index() {return "home";}

    @GetMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "error/403";
    }

    @ResponseBody
    @PostMapping("/doLogin")
    public Result login(String username,String password) {
        Result r = loginService.login(username, password);
        return r;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/sys-user/list")
    public String toUser() {
        return "user/user-list";
    }
}
