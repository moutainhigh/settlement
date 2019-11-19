package com.settlement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.SysRole;
import com.settlement.entity.SysUser;
import com.settlement.entity.SysUserRole;
import com.settlement.service.LoginService;
import com.settlement.service.SysRoleService;
import com.settlement.service.SysUserRoleService;
import com.settlement.service.SysUserService;
import com.settlement.utils.Result;
import com.settlement.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

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

    /**
     * @description 用户列表页面
     *
     * @return
     */
    @GetMapping("/sys-user/list")
    public String userPage() {
        return "user/list";
    }

    /**
     *@desciption 用户添加页面
     *
     * @return
     */
    @GetMapping("/sys-user/add")
    public String toUserAdd() {
        return "user/add";
    }

    /**
     * @description 用户编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/sys-user/edit/{id}")
    public String toUserEdit(@PathVariable  Integer id, Model model) {
        // 根据ID 取得用户信息
        SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("id", id));
        SysUserRole userRole = sysUserRoleService.getOne(new QueryWrapper<SysUserRole>().eq("user_id",id));
        SysUserVo userVo = new SysUserVo(user.getId(),user.getEmail(),null,user.getEmployeeNo(),user.getRealName(),user.getCity(),user.getMobile(),user.getEnabled(),user.getCreateUserId(),user.getCreateTime(),user.getDeptId(),user.getDelFlag(),null);
        userVo.setRole(new SysRole().setId(userRole.getRoleId()));
        // 根据部门ID，查出角色
        List<SysRole> roles = (List<SysRole>)sysRoleService.getRolesByDeptId(user.getDeptId()).getData();
        model.addAttribute("userVo",userVo);
        model.addAttribute("roles",roles);
        return "user/edit";
    }

    /**
     * @description 用户停用页面
     *
     * @return
     */
    @GetMapping("/sys-user/stop/{id}")
    public String toUserStop(@PathVariable  Integer id, Model model) {
        SysUserVo userVo = new SysUserVo();
        model.addAttribute("userVo",userVo);
        return "user/stop";
    }

    @GetMapping("/sys-role/list")
    public String toRolePageList() {
        return "role/role-list";
    }

    @GetMapping("/sys-permission/list")
    public String toPermissionList() {
        //public String toPermissionList(@RequestParam(defaultValue = "update") String mode, Model model) {
        //model.addAttribute("mode",mode);
        return "permission/permission-list";
    }
    @GetMapping("/sys-dept/list")
    public String toDeptList() {
        //public String toPermissionList(@RequestParam(defaultValue = "update") String mode, Model model) {
        //model.addAttribute("mode",mode);
        return "dept/dept-list";
    }

}
