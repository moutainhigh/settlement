package com.settlement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.*;
import com.settlement.service.*;
import com.settlement.utils.Const;
import com.settlement.utils.Result;
import com.settlement.vo.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SysDataDicService sysDataDicService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private BaProjectGroupService baProjectGroupService;
    @Autowired
    private BaTimeParamService baTimeParamService;
    @Autowired
    private BaExportParamService baExportParamService;
    @Autowired
    private BaFormulaParamService baFormulaParamService;
    @Autowired
    private BaCustomerService baCustomerService;
    @Autowired
    private BaContractService baContractService;
    @Autowired
    private BaWorkAttendanceService baWorkAttendanceService;
    @Autowired
    private BaLevelPriceService baLevelPriceService;
    @Autowired
    private BaProjectGroupAssistantService baProjectGroupAssistantService;
    @Autowired
    private BaApplyService baApplyService;
    @Autowired
    private BaEmployeeService baEmployeeService;
    @Autowired
    private BaProjectGroupCheckService baProjectGroupCheckService;
    @Autowired
    private BaProjectGroupSettlementService baProjectGroupSettlementService;
    @Autowired
    private BaApplyEmployeeService baApplyEmployeeService;
    @Autowired
    private BaEmpApplyCheckService baEmpApplyCheckService;
    @Autowired
    private BaEmpLeavePgService baEmpLeavePgService;
    @Autowired
    private BaEmpLeaveJobService baEmpLeaveJobService;
    @Autowired
    private BaCityService baCityService;

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
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return r;
    }
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
///////////////////////////////////////////////////////////////////////////   用户跳转页面 start //////////////////////////////////////////////////////////////
    /**
     * @description 用户列表页面
     *
     * @return
     */
    @RequiresPermissions("user:list")
    @GetMapping("/sys-user/list")
    public String userPage() {
        return "user/list";
    }

    /**
     *@desciption 用户添加页面
     *
     * @return
     */
    @RequiresPermissions("user:add")
    @GetMapping("/sys-user/add")
    public String toUserAdd(Model model) {
        List<BaCity> cities = baCityService.getBaCityList();
        model.addAttribute("cities",cities);
        return "user/add";
    }

    /**
     * @description 用户编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("user:edit")
    @GetMapping("/sys-user/edit/{id}")
    public String toUserEdit(@PathVariable  Integer id, Model model) {
        // 根据ID 取得用户信息
        SysUser user = this.sysUserService.getOne(new QueryWrapper<SysUser>().eq("id", id));
        SysUserRole userRole = sysUserRoleService.getOne(new QueryWrapper<SysUserRole>().eq("user_id",id));
        SysUserVo userVo = new SysUserVo(user.getId(),user.getEmail(),null,user.getEmployeeNo(),user.getRealName(),user.getCity(),user.getMobile(),user.getEnabled(),user.getCreateUserId(),user.getCreateTime(),user.getDeptId(),user.getDelFlag(),null);
        userVo.setRole(new SysRole().setId(userRole.getRoleId()));
        // 根据部门ID，查出角色
        List<SysRole> roles = (List<SysRole>)sysRoleService.getRolesByDeptId(user.getDeptId()).getData();
        List<BaCity> cities = baCityService.getBaCityList();
        model.addAttribute("cities",cities);
        model.addAttribute("userVo",userVo);
        model.addAttribute("roles",roles);
        return "user/edit";
    }

    /**
     * @description 用户停用页面
     *
     * @auth admin
     * @date 2019-1-3
     * @return
     */
    @RequiresPermissions("user:stop")
    @GetMapping("/sys-user/stop/{id}")
    public String toUserStop(@PathVariable  Integer id, Model model) {
        // 根据数据字典 取得停用原因
        List<SysDataDicVo> stopTypeList = this.sysDataDicService.getDataDicSelectByParentCode(Const.USER_STOP_TYPE_CODE);
        model.addAttribute("stopTypeList", stopTypeList);
        model.addAttribute("userVo",sysUserService.getUserStop(id));
        // 同部门下的同角色用户
        SysUser user = this.sysUserService.getById(id);
        SysUserRole sur = this.sysUserRoleService.getRoleByUserId(id);
        List<SelectVo> userSelect = (List<SelectVo>) this.sysUserService.getUserSelectByRoleDept(sur.getRoleId(), id, user.getDeptId()).getData();
        model.addAttribute("userSelect", userSelect);
        return "user/stop";
    }

    /**
     * 密码修改页面
     * @return
     */
    @GetMapping("/sys-user/updatepassword")
    public String updatePassword() {
        return "user/updatepassword";
    }
    /////////////////////////////////////////////////////////////////////////////////   用户跳转页面 end //////////////////////////////////////////////////////////////

    @RequiresPermissions("role:list")
    @GetMapping("/sys-role/list")
    public String toRolePageList() {
        return "role/list";
    }

    /**
     * 跳转角色添加页面
     * @return
     */
    @RequiresPermissions({"role:toAdd","role:toUpdate"})
    @GetMapping("/sys-role/toAddorUpdate/{mode}/{id}")
    public String toRoleAdd(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            Map<SysPermission,List<Map<SysPermission, List<SysPermission>>>> maproot = sysPermissionService.getPermissons();
            model.addAttribute("maproot",maproot);
            model.addAttribute("mode",Const.MODE_ADD);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            SysRole sysRole = sysRoleService.getById(id);
            Map<SysPermissionVo,List<Map<SysPermissionVo, List<SysPermissionVo>>>> maproot = sysPermissionService.getPermissons(id);
            model.addAttribute("maproot",maproot);
            model.addAttribute("sysRole",sysRole);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "role/add";
    }

    /**
     * 权限分配页
     * @param roleId
     * @param model
     * @return
     */
    @RequiresPermissions("role:toAssignRole")
    @GetMapping("/sys-role/toAssignRole/{roleId}")
    public String toAssignRole(@PathVariable(value="roleId") Integer roleId,Model model){
        model.addAttribute("roleId",roleId);
        return "role/assign_role";
    }
    /////////////////////////////////////////////////////////////////////////////////   角色页面 end //////////////////////////////////////////////////////////////
    /**
     * 菜单管理列表
     * @return
     */
    @RequiresPermissions("permission:list")
    @GetMapping("/sys-permission/list")
    public String toPermissionList() {
        return "permission/list";
    }

    /**
     * 添加菜单
     * @param id
     * @param mode
     * @param model
     * @return
     */
    @RequiresPermissions({"permission:toAdd","permission:toUpdate"})
    @GetMapping("/sys-permission/permContent")
    public String permissionIframeContent(@RequestParam(required =false,defaultValue = "1")  Integer id, @RequestParam(required = false,defaultValue = "") String mode, Model model) {
        //菜单类型
        List<SysDataDicVo> sysDataDicVos =sysDataDicService.getDataDicSelectByParentCode(Const.PERMISSION_TYPE_CODE);
        model.addAttribute("sysDataDicVos",sysDataDicVos);
        if("update".equals(mode)) {
            SysPermissionVo sysPermission =sysPermissionService.getSysPermissionVoById(id);
            model.addAttribute("mode","update");
            model.addAttribute("sysPermission",sysPermission);
        } else if ("add".equals(mode)) {
            SysPermissionVo sysPermissionP =sysPermissionService.getSysPermissionVoById(id);
            SysPermissionVo sysPermission = new SysPermissionVo();
            sysPermission.setParentId(id);
            sysPermission.setParentContent(sysPermissionP.getPName());
            sysPermission.setType(Const.MENU_TYPE_M);
            model.addAttribute("mode", "add");
            model.addAttribute("sysPermission",sysPermission);
        } else {
            SysPermissionVo sysPermission = new SysPermissionVo(); //sysPermissionService.getRootSysPermissionVoById(Const.PERMISSION_ROOT_ID);
            sysPermission.setType(Const.MENU_TYPE_B);
            model.addAttribute("mode","default");
            model.addAttribute("sysPermission",sysPermission);
        }
        return "permission/permContent";
    }
    /////////////////////////////////////////////////////////////////////////////////   权限页面 end //////////////////////////////////////////////////////////////

    /**
     * 城市页面
     * @return
     */
    @RequiresPermissions("city:list")
    @GetMapping("/ba-city/list")
    public String toCityList() {
        return "city/list";
    }

    /**
     * 城市 新增 修改
     * @param mode
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions({"city:toUpdate","city:toAdd"})
    @GetMapping("/ba-city/toAddorUpdate/{mode}/{id}")
    public String toBaCityAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
            BaCity baCity = new BaCity();
            model.addAttribute("baCity",baCity);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            BaCity baCity = baCityService.getById(id);
            model.addAttribute("baCity",baCity);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "city/add";
    }
    /////////////////////////////////////////////////////////////////////////////////   城市页面 end //////////////////////////////////////////////////////////////
    /**
     * 跳转到部门列表页
     * @return
     */
    @RequiresPermissions("dept:list")
    @GetMapping("/sys-dept/list")
    public String toDeptList() {
        return "dept/list";
    }

    /**
     * 添加部门
     * @param id
     * @param mode
     * @param model
     * @return
     */
    @RequiresPermissions({"dept:toUpdate","dept:toAdd"})
    @GetMapping("/sys-dept/deptContent")
    public String deptIframeContent(@RequestParam(required =false,defaultValue = "1") Integer id, @RequestParam(required = false,defaultValue = "view") String mode, Model model) {
        List<SysRoleVo> sysRoleVos = sysRoleService.getRoleVoExclude(Const.ROLE_CODE_ADMIN);
        if("update".equals(mode)) {
            SysDeptVo sysDept=sysDeptService.getSysDeptVoById(id);
            sysRoleVos = sysRoleService.getRoleVoByDeptId(sysDept.getId());
            model.addAttribute("sysRoleVos",sysRoleVos);
            model.addAttribute("mode","update");
            model.addAttribute("sysDept",sysDept);
        } else if ("add".equals(mode)) {
            SysDeptVo sysDept = new SysDeptVo();
            SysDeptVo sysDeptP=sysDeptService.getSysDeptVoById(id);
            sysDept.setParentId(id);
            sysDept.setParentContent(sysDeptP.getDeptName());
            model.addAttribute("mode", "add");
            model.addAttribute("sysDept",sysDept);
        } else {
            SysDeptVo sysDept=sysDeptService.getSysDeptVoById(id);
            sysRoleVos = sysRoleService.getRoleVoByDeptId(sysDept.getId());
            model.addAttribute("sysRoleVos",sysRoleVos);
            model.addAttribute("mode","view");
            model.addAttribute("sysDept",sysDept);
        }
        model.addAttribute("sysRoleVos",sysRoleVos);
        return "dept/deptContent";
    }

/////////////////////////////////////////////////////////////////////////////////   部门页面 end //////////////////////////////////////////////////////////////

    /**
     * 数据字典列表
     * @return
     */
    @RequiresPermissions("dic:list")
    @GetMapping("/sys-data-dic/list")
    public String toDatatDicList() {
        return "dic/list";
    }

    /**
     *@desciption 数据字典添加页面
     *
     * @return
     */
    @RequiresPermissions({"dic:toUpdate","dic:toAdd"})
    @GetMapping("/sys-data-dic/toAddOrUpdate/{mode}/{id}")
    public String toDataDicAdd(@PathVariable(value = "mode") String mode, @PathVariable(value="id",required = false) Integer id, Model model) {

       QueryWrapper<SysDataDic> dicQueryWrapper = new QueryWrapper<>();
        dicQueryWrapper.eq("pid",Const.DATA_DIC_ROOT);
       List<SysDataDic> sysDataDicList = sysDataDicService.list(dicQueryWrapper);
        if(Const.MODE_ADD.equals(mode)) {
//            SysDataDic sysDataDic = new SysDataDic();
//            if(id.equals(Const.DATA_DIC_ROOT)){
//                sysDataDic.setId(Const.DATA_DIC_ROOT);
//                sysDataDic.setPid("0");
//            } else {
//                QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("id", id);
//                sysDataDic = sysDataDicService.getOne(queryWrapper);
//            }
            SysDataDic sysDataDic = sysDataDicService.getRoot(Const.DATA_DIC_ROOT);
            Integer sort=(Integer)sysDataDicService.getChildSort(Const.DATA_DIC_ROOT).getData();
            sysDataDic.setSort(sort);
            model.addAttribute("mode","add");
            model.addAttribute("sysDataDic",sysDataDic);
        } else if(Const.MODE_UPDADTE.equals(mode)) {
            QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            SysDataDic sysDataDic = sysDataDicService.getOne(queryWrapper);
            model.addAttribute("mode","update");
            model.addAttribute("sysDataDic",sysDataDic);
        }
        model.addAttribute("sysDataDicList",sysDataDicList);
        return "dic/add";
    }


    /////////////////////////////////////////////////////////////  项目组跳转start ///////////////////////////////////////////////////////////////////

    /**
     * @description 项目组列表
     *
     * @auth    admin
     * @dte     2019-11-20
     */
    @RequiresPermissions("group:list")
    @GetMapping("/ba-project-group/list")
    public String toPgList() {
        return "pg/list";
    }

    /**
     * @description 项目组添加
     *
     * @auth admin
     * @date 2019-11-21
     * @return
     */
    @RequiresPermissions("group:add")
    @GetMapping("/ba-project-group/add")
    public String toPgAdd(Model model) {
        // 取得客户经理下拉列表
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deptId",user.getDeptId());
        paramMap.put("amRoleCode",Const.ROLE_CODE_AM);
        paramMap.put("enabled", Const.ENABLED_Y);
        model.addAttribute("amList", this.sysUserService.getAmListByDeptAndRole(paramMap));
        return "pg/add";
    }

    /**
     * @description 项目组编辑
     *
     * @auth admin
     * @date 2019-11-25
     * @return
     */
    @RequiresPermissions("group:edit")
    @GetMapping("/ba-project-group/edit/{id}")
    public String toPgEdit(@PathVariable Integer id, Model model) {
        model.addAttribute("pg",this.baProjectGroupService.getById(id));
        // 取得柯经理下来列表
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deptId",user.getDeptId());
        paramMap.put("amRoleCode",Const.ROLE_CODE_AM);
        paramMap.put("enabled", Const.ENABLED_Y);
        model.addAttribute("amList", this.sysUserService.getAmListByDeptAndRole(paramMap));
        return "pg/edit";
    }

    /**
     * @description 关联助理
     *
     * @auth admin
     * @date 2019-11-26
     * @param id
     * @return
     */
    @RequiresPermissions("group:relateAssistant")
    @GetMapping("/ba-project-group/relate-assistant/{id}")
    public String toRelateAssistant(@PathVariable Integer id, Model model) {
        model.addAttribute("pg",this.baProjectGroupService.getProjectGroupAssistantById(id));
        return "pg/relate-assistant";
    }

    /**
     * @description 关联结算负责人
     *
     * @auth admin
     * @date 2019-11-27
     * @param id
     * @return
     */
    @RequiresPermissions("group:relateSettlement")
    @GetMapping("/ba-project-group/relate-settlement/{id}")
    public String toRelateSettlement(@PathVariable Integer id, Model model) {
        model.addAttribute("pg", this.baProjectGroupService.getProjectGroupSettlementById(id));
        return "pg/relate-settlement";
    }
    @RequiresPermissions("group:start")
    @GetMapping("/ba-project-group/start/{id}")
    public String toStartPg(@PathVariable(value="id") Integer id,Model model) {
        BaProjectGroup bpg = this.baProjectGroupService.getById(id);
        model.addAttribute("projectGroupVo", bpg);
        model.addAttribute("projectGroupAssistantVoList",this.baProjectGroupAssistantService.getProjectGroupAssistantDetailBypgId(id));
        model.addAttribute("projectSettlementVoList",this.baProjectGroupSettlementService.getProjectGroupSettlementDetailByPgId(id));
        model.addAttribute("customer",this.baCustomerService.getById(bpg.getCustomerId()));
        return "pg/start";
    }

    /**
     * 项目组移交user
     * @param ids
     * @param model
     * @return
     */
    @RequiresPermissions("group:transfer")
    @GetMapping("/ba-project-group/transfer/{ids}")
    public String toTransferPage(@PathVariable Integer[] ids,Model model){
        SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        SysUserRole sysRole = sysUserRoleService.getRoleByUserId(user.getId());
        List<SysUserVo> amUsers = (List<SysUserVo>)sysUserService.getUserByDeptIdAndRoleCode(user.getDeptId()).getData();
        List<SysUser> sysUsers = (List<SysUser>)sysUserService.getUserSelectByRoleDept(sysRole.getRoleId(),user.getId(),user.getDeptId()).getData();
        model.addAttribute("ids",ids);
        model.addAttribute("applyUser",user.getId());
        model.addAttribute("amUsers",amUsers);
        model.addAttribute("users",sysUsers);
        return "pg/transfer";
    }
    /////////////////////////////////////////////////////////////  项目组跳转end  ///////////////////////////////////////////////////////////////////

    /**
     * 跳转时间点参数页面
     * @return
     */
    @RequiresPermissions("time:list")
    @GetMapping("/ba-time-param/list")
    public String toTimeParam(){
        return "timeparam/list";
    }

    /**
     * 时间点-关联项目组
     * @return
     */
    @RequiresPermissions("time:relateProject")
    @GetMapping("/ba-time-param/relate/project/{id}/{type}")
    public String toRelateProject(@PathVariable Integer id,@PathVariable String type, Model model){
        model.addAttribute("id",id);
        model.addAttribute("type",type);
        return "timeparam/project";
    }

    /**
     * 跳转时间参数添加页面
     * @return
     */
    @RequiresPermissions({"time:toUpdate","time:toAdd"})
    @GetMapping("/ba-time-param/toAddorUpdate/{mode}/{id}")
    public String toTimeParamAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        //时间点类型
        List<SysDataDicVo> sysDataDicVos =sysDataDicService.getDataDicSelectByParentCode(Const.TIME_PARAM_PARENT);
        model.addAttribute("sysDataDicVos",sysDataDicVos);
        List<BaCustomer> baCustomers = baCustomerService.list();
        if(Const.MODE_ADD.equals(mode)) {
            BaTimeParamVo baTimeParam = new BaTimeParamVo();
            baTimeParam.setType(Const.TIME_PRAMA_STOP);
            model.addAttribute("baTimeParam",baTimeParam);
            model.addAttribute("mode",Const.MODE_ADD);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            BaTimeParamVo baTimeParam = baTimeParamService.getTimeParamVoById(id);

//            List<BaProjectGroup> baProjectGroups = (List<BaProjectGroup>)baProjectGroupService.getGroupsByCustomerId(baTimeParam.getCustomerId()).getData();
            model.addAttribute("baTimeParam",baTimeParam);
//            model.addAttribute("baProjectGroups",baProjectGroups);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        model.addAttribute("baCustomers",baCustomers);
        return "timeparam/add";

    }
/////////////////////////////////////////////////////////////////////////////////   时间参数页面 end //////////////////////////////////////////////////////////////
    /**
     * 跳转导出参数页面
     * @return
     */
    @RequiresPermissions("export:list")
    @GetMapping("/ba-export-param/list")
    public String toExportParam(){
        return "exportparam/list";
    }


    /**
     * 跳转角色添加页面
     * @return
     */
    @RequiresPermissions({"export:toUpdate","export:toAdd"})
    @GetMapping("/ba-export-param/toAddorUpdate/{mode}/{id}")
    public String toExportParamAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            BaExportParam baExportParam = baExportParamService.getById(id);
            model.addAttribute("baExportParam",baExportParam);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "exportparam/add";
    }

/////////////////////////////////////////////////////////////////////////////////   导出参数页面 end //////////////////////////////////////////////////////////////
    /**
     * 跳转结算公式参数页面
     * @return
     */
    @RequiresPermissions("formula:list")
    @GetMapping("/ba-formula-param/list")
    public String toFormulaParam(){
        return "formulaparam/list";
    }


    /**
     * 跳转角色添加页面
     * @return
     */
    @RequiresPermissions({"formula:toUpdate","formula:toAdd"})
    @GetMapping("/ba-formula-param/toAddorUpdate/{mode}/{id}")
    public String toFormulaParamAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
        } else if(Const.MODE_UPDADTE.equals(mode)){
           BaFormulaParam baFormulaParam = baFormulaParamService.getById(id);
            model.addAttribute("baFormulaParam",baFormulaParam);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "formulaparam/add";
    }
/////////////////////////////////////////////////////////////////////////////////   结算页面 end //////////////////////////////////////////////////////////////
    /**
     * 跳转结算公式参数页面
     * @return
     */
    @RequiresPermissions("customer:list")
    @GetMapping("/ba-customer/list")
    public String toBaCustomer(){
        return "customer/list";
    }


    /**
     * 跳转角色添加页面
     * @return
     */
    @RequiresPermissions({"customer:toUpdate","customer:toAdd"})
    @GetMapping("/ba-customer/toAddorUpdate/{mode}/{id}")
    public String toBaCustomerAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
            BaCustomerVo baCustomerVo = new BaCustomerVo();
            model.addAttribute("baCustomer",baCustomerVo);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            SysDept sysDept = sysDeptService.getDeptByCustomerId(id);
            //List<SysDeptRoleUserVo> chiefs = (List<SysDeptRoleUserVo>)sysDeptService.getDeptRoleUsers(sysDept.getId()).getData();
            List<SysUserVo> chiefs = (List<SysUserVo>) sysUserService.getUserByDeptIdAndRoleCode(sysDept.getId()).getData();
            BaCustomerVo baCustomerVo = baCustomerService.getBaCustomerVoById(id);
            model.addAttribute("baCustomer",baCustomerVo);
            model.addAttribute("chiefs",chiefs);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "customer/add";
    }
    /**
     * 客户移交user
     * @param ids
     * @param model
     * @return
     */
    @RequiresPermissions("customer:transfer")
    @GetMapping("/ba-customer/transfer/{ids}")
    public String toTransferCustomerPage(@PathVariable Integer[] ids,Model model){
        SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        SysUserRole sysRole = sysUserRoleService.getRoleByUserId(user.getId());
        List<SysUser> sysUsers = (List<SysUser>)sysUserService.getUserSelectByRoleDept(sysRole.getRoleId(),user.getId(),user.getDeptId()).getData();
        model.addAttribute("ids",ids);
        model.addAttribute("applyUser",user.getId());
        model.addAttribute("users",sysUsers);
        return "customer/transfer";
    }
    /////////////////////////////////////////////////////////////////////////////////   客户页面 end //////////////////////////////////////////////////////////////
    /**
     * @description 员工录入
     *
     * @auth admin
     * @date 2019-11-28
     * @param pgId
     * @return
     */
    @RequiresPermissions("employee:enterEmp")
    @GetMapping("/ba-employee/enter-emp/{pgId}")
    public String toEnterEmployPage(@PathVariable  Integer pgId, Model model) {
        model.addAttribute("pgId", pgId);
        return "emp/enter";
    }

    /**
     * @description 入场员工
     *
     * @auth admin
     * @date 2019-12-20
     * @param pgId
     * @param model
     * @return
     */
    @RequiresPermissions("employee:entranceEmp")
    @GetMapping("/ba-employee/entrance-emp/{pgId}")
    public String toDetailEmpPage(@PathVariable  Integer pgId, Model model) {
        model.addAttribute("pgId", pgId);
        // 结算时间点，结算完成时间点
        model.addAttribute("stopTimeParam", this.baTimeParamService.getStopTimeParam(pgId));
        model.addAttribute("completeTimeParam", this.baTimeParamService.getCompleteParam(pgId));
        return "emp/entrance";
    }

    /**
     * 添加员工
     *
     * @auth admin
     * @date 2019-11-29
     * @param pgId
     * @param model
     * @return
     */
    @RequiresPermissions("employee:add")
    @GetMapping("/ba-employee/add/{pgId}")
    public String toAddEmployeePage(@PathVariable Integer pgId, Model model) {
        List<BaCity> cities = baCityService.getBaCityList();
        model.addAttribute("cities",cities);
        // 级别填写模式
        // model.addAttribute("levelTypeSelect", this.sysDataDicService.getDataDicSelectByParentCode(Const.LEVEL_TYPE_PARENT_CODE));
        // 查询项目组关联的级别
        model.addAttribute("levelPriceList", this.baLevelPriceService.getLevelPriceByPgId(pgId));
        // 单位
        model.addAttribute("unitList", sysDataDicService.getDataDicSelectByParentCode(Const.UNIT_PARENT_CODE));
        model.addAttribute("pgId",pgId);
        return "emp/add";
    }

    /**
     * @description 编辑员工页面
     *
     * @auth admin
     * @date 2019-12-10
     * @param id
     * @param pgId
     * @param model
     * @return
     */
    @RequiresPermissions("employee:edit")
    @GetMapping("/ba-employee/edit/{id}/{pgId}")
    public String toEditEmployee(@PathVariable Integer id, @PathVariable Integer pgId, Model model) {
        List<BaCity> cities = baCityService.getBaCityList();
        model.addAttribute("cities",cities);
        model.addAttribute("levelPriceList", this.baLevelPriceService.getLevelPriceByPgId(pgId));
        // 单位
        model.addAttribute("unitList", sysDataDicService.getDataDicSelectByParentCode(Const.UNIT_PARENT_CODE));
        model.addAttribute("emp",this.baEmployeeService.getProjectEmpById(id));
        return "emp/edit";
    }

    /**
     * @description 预览图片
     *
     * @auth admin
     * @date 2019-12-10
     * @param id
     * @return
     */
    @RequiresPermissions("employee:view")
    @GetMapping("/ba-employee/view/{id}")
    public String toViewImg(@PathVariable Integer id, Model model) {
        model.addAttribute("imgSrc", this.baEmployeeService.getProjectEmpById(id).getRateEmailFilename());
        return "emp/view";
    }

    /**
     * @description 员工申请修改
     *
     * @auth admin
     * @date 2019-12-23
     * @param ids              员工ID
     * @param pgId             项目组ID
     * @param model
     * @return
     */
    @RequiresPermissions("employee:apply")
    @GetMapping("/ba-employee/apply/{ids}/{pgId}")
    public String toEmpApplyPage(@PathVariable(value="ids") String ids, @PathVariable(value="pgId") Integer pgId, Model model) {
        BaProjectGroup bpg = this.baProjectGroupService.getById(pgId);
        SysUser checkUser = this.sysUserService.getById(bpg.getCheckUserId());
        ProjectGroupVo bpgv = new ProjectGroupVo();
        bpgv.setId(bpg.getId());
        bpgv.setPgName(bpg.getPgName());
        bpgv.setCheckUserId(bpg.getCheckUserId());
        bpgv.setCheckUserName(checkUser.getRealName());
        model.addAttribute("pgVo",bpgv);
        model.addAttribute("empIds",ids);
        model.addAttribute("empList", this.baEmployeeService.getApplyUpdateEmps(ids));
        return "emp/apply";
    }

//////////////////////////////////////////////////////////////////////////////////////////////员工页面 end///////////////////////////////////////////////////////////////////
    /**
     * 跳转合同页面
     * @return
     */
    @RequiresPermissions("contract:list")
    @GetMapping("/ba-contract/list")
    public String toBaContract(){
        return "contract/list";
    }


    /**
     * 跳转合同添加页面
     * @return
     */
    @RequiresPermissions({"contract:toUpdate","contract:toAdd"})
    @GetMapping("/ba-contract/toAddorUpdate/{mode}/{id}")
    public String toBaContractAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {

        List<BaCustomer> baCustomers = baCustomerService.list();
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
            BaContractVo baContract = new BaContractVo();
            model.addAttribute("baContract",baContract);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            BaContractVo baContractVo = baContractService.getBaContractVoById(id);
            List<BaProjectGroup> baProjectGroups =(List<BaProjectGroup>) baProjectGroupService.getGroupsByCustomerId(baContractVo.getCustomerId()).getData();
            model.addAttribute("baProjectGroups",baProjectGroups);
            model.addAttribute("baContract",baContractVo);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        model.addAttribute("baCustomers",baCustomers);
        return "contract/add";
    }
    ////////////////////////////////////////////////////////////////////////////////合同管理页面 end/////////////////////////////////////////////////////////////////////////////////

    /**
     * 考勤管理页面
     * @return
     */
    @RequiresPermissions("attendance:list")
    @GetMapping("/ba-work-attendance/list")
    public String toWorkAttendance(){
        return "workattendance/list";
    }
    /**
     * 考勤管理-编辑考勤信息
     * @param projectId 项目id
     * @return
     */
    @RequiresPermissions("attendance:attendlist")
    @GetMapping("/ba-work-attendance/attendlist")
    public String toAttendList(Integer projectId,Model model){
        List<Integer> years = baTimeParamService.getTimeYearValue();
        List<String> months = baTimeParamService.getTimeMonthValue();
        List<SysDataDicVo> subStatusList = sysDataDicService.getDataDicSelectByParentCode(Const.SUB_STATUS);
        Integer applyCount = 0;
        Integer totalApplyCount = 3;
        String stopTime =baTimeParamService.getStopTimeParam(projectId);
        String compelteTime = baTimeParamService.getCompleteParam(projectId);
        if(projectId!=null) {
            BaProjectGroup baProjectGroup = baProjectGroupService.getById(projectId);
            //BaProjectGroupAssistant baProjectGroupAssistant = baProjectGroupAssistantService.getBaProjectGroupAssistantByGroupId(projectId);
            String applyTime= baTimeParamService.getCurrentMonthYear();
            applyCount = (Integer)baApplyService.getApplyCountByProjectId(projectId,applyTime).getData();
            //绑定审核人AM
            model.addAttribute("checkUserId", baProjectGroup.getCheckUserId());
            model.addAttribute("projectId", projectId);
        }
        String tipCountMessage = "本月您已经修改"+applyCount+"次,还有"+(totalApplyCount-applyCount)+"次修改机会";
        String tipStopAndCompelteTime = "";
        if(!StringUtil.isNotBlank(stopTime)) {
            stopTime="尚未设置【结算时间点】";
        }
        if(!StringUtil.isNotBlank(compelteTime)) {
            compelteTime=" 尚未设置【结算完成时间点】";
        }
        model.addAttribute("subStatusList",subStatusList);
        model.addAttribute("tipStopAndCompelteTime",tipStopAndCompelteTime);
        model.addAttribute("tipCountMessage",tipCountMessage);
        model.addAttribute("totalApplyCount",totalApplyCount);
        model.addAttribute("applyCount",applyCount);
        model.addAttribute("years",years);
        model.addAttribute("months",months);
        model.addAttribute("stopTime",stopTime);
        model.addAttribute("compelteTime",compelteTime);
        return "workattendance/attendlist";
    }
    /**
     * 考勤管理-申请修改
     * @return
     */
    @RequiresPermissions("attendance:applymodify")
    @GetMapping("/ba-work-attendance/applymodify/{checkUserId}/{projectId}/{ids}")
    public String toApplymodifyPage(@PathVariable Integer checkUserId,@PathVariable Integer projectId,@PathVariable Integer[] ids,Model model, HttpSession session){
        model.addAttribute("workAttendanceIds",ids);
        //申请人
        SysUser applyUser = (SysUser)session.getAttribute("user");
        //审核人AM
        SysUser checkUser= sysUserService.getById(checkUserId);
        ApplyAndCheckUserVo applyAndCheckUserVo = new ApplyAndCheckUserVo(applyUser.getId(),checkUser.getId(),checkUser.getRealName(),projectId,ids);
        model.addAttribute("applyAndCheckUserVo",applyAndCheckUserVo);
        return "workattendance/applymodify";
    }

    /**
     * 考勤管理-编辑员工考勤
     * @param id 考勤id
     * @param model
     * @return
     */
    @RequiresPermissions("attendance:edit")
    @GetMapping("/ba-work-attendance/edit/{id}")
    public String toWorkAttendanceEdit(@PathVariable Integer id, Model model){
        BaWorkAttendanceVo baWorkAttendanceVo = baWorkAttendanceService.getBaWorkAttendanceVoById(id);
        model.addAttribute("baWork",baWorkAttendanceVo);
        return "workattendance/add";
    }

    /**
     * 考勤管理-生成考勤记录
     * @param projectId
     * @param model
     * @return
     */
    @RequiresPermissions("attendance:generate")
    @GetMapping("/ba-work-attendance/generate/{projectId}")
    public String toGenerateWorkAttendance(@PathVariable Integer projectId,Model model){
        model.addAttribute("pgId",projectId);
        return "workattendance/generate";
    }
    /**
     * 考勤申请修改记录-通过审核-修改考勤信息页面
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("attendance:workattendancelist")
    @GetMapping("/ba-work-attendance/apply/workattendancelist/{id}")
   public String toApplyWorkattendanceList(@PathVariable Integer id, Model model) {
        model.addAttribute("applyId",id);
        return "apply/applyattendlist";
    }
    ///////////////////////////////////////////////////////////////////////////////////考勤//////////////////////////////////////////////////////////////////////////////

    /**
     * 考勤审核
     * @param model
     * @return
     */
    @RequiresPermissions("applyCheckWorkattendance:list")
    @GetMapping("/ba-apply-check-workattendance/list")
    public String toCheckattendanceList(Model model){
        List<SysDataDic> checkStatusList = sysDataDicService.getCheckStatus();
        model.addAttribute("checkStatusList",checkStatusList);
        return "checkworkattendance/list";
    }

    /**
     * 审核操作
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("applyCheckWorkattendance:check")
    @GetMapping("/ba-apply/check/workattendance/{id}")
    public String toCheckattendanceList(@PathVariable Integer id, Model model){
        BaApplyVo baApplyVo = baApplyService.getApplyVoById(id);
        List<SysDataDic> checkStatusList = sysDataDicService.getCheckStatus();
        model.addAttribute("baApply",baApplyVo);
        model.addAttribute("checkStatusList",checkStatusList);
        return "checkworkattendance/check";
    }
    /**
     * 申请修改记录
     * @return
     */
    @RequiresPermissions("apply:list")
    @GetMapping("/ba-apply/list")
    public String toApplyList(Model model){
        List<SysDataDic> checkStatusList = sysDataDicService.getCheckStatus();
        model.addAttribute("checkStatusList",checkStatusList);
        return "apply/list";
    }

    /**
     * 审核 申请移交
     * @param model
     * @return
     */
    @RequiresPermissions("transfercheck:list")
    @GetMapping("/ba-apply-transfer/check/list")
    public String toApplyTransferCheckList(Model model){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal() ;
        SysRole sysRole = sysRoleService.findRoleByUserId(user.getId());
        List<SysDataDicVo> checkStatusList = sysDataDicService.getDataDicSelectByParentCode(Const.CHECK_STATUS_PARENT_CODE);
        List<SysDataDicVo> applyTypeList = sysDataDicService.getDataDicSelectByParentCode(Const.APPLY_TRANSFER);
        model.addAttribute("checkStatusList",checkStatusList);
        model.addAttribute("applyTypeList",applyTypeList);
        if(sysRole.getRoleCode().equals(Const.ROLE_CODE_AM)) {
            model.addAttribute("mode", "N");
        }else {
            model.addAttribute("mode","Y");
        }
        return "transfercheck/list";
    }

    /**
     * 跳转移交审核页面
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("transfercheck:check")
    @GetMapping("/ba-apply-transfer/check/check/{id}")
    public String toApplyTransferCheck(@PathVariable Integer id, Model model){
        List<SysDataDicVo> checkStatusList = sysDataDicService.getDataDicSelectByParentCode(Const.CHECK_RESULT_CODE);
        model.addAttribute("checkStatusList",checkStatusList);
        model.addAttribute("applyId",id);
        return "transfercheck/check";
    }

    /**
     * 跳转移交详细详细
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("transfercheck:detail")
    @GetMapping("/ba-apply-transfer/check/detail/{id}")
    public String toApplyTransferCheckDetail(@PathVariable Integer id,Model model){
        model.addAttribute("applyId",id);
        return "transfercheck/detail";
    }
    /**
     * 申请修改考勤记录
     * @param id
     * @return
     */
    @RequiresPermissions("apply:workattendance")
    @GetMapping("/ba-apply/workattendance/{id}")
    public String toApplyWorkAttendanceList(@PathVariable Integer id) {
        return "apply/applyattendlist";
    }

    /**
     * 加载考勤申请记录 Assistant
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("apply:workattendancelist")
    @GetMapping("/ba-apply/workattendancelist/{id}")
    public String toApplyWorkattendancelist(@PathVariable Integer id,Model model){
        model.addAttribute("applyId",id);
        // model.addAttribute("mode","check");
        return "apply/attendlist";
    }
    /**
     * 跳转口令验证页面
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("apply:passcode")
    @GetMapping("/ba-apply/passcode/{id}")
    public String toApplyPassCode(@PathVariable Integer id,Model model){
        BaApply baApply = baApplyService.getById(id);
        model.addAttribute("baApply",baApply);
        return "apply/passcode";
    }



    /////////////////////////////////////////////////////////////////////////////////////申请 审核////////////////////////////////////////////////////////////////////////////

    /**
     * @description 项目组审核列表页
     *
     * @auth admin
     * @date 2019-12-10
     * @return
     */
    @RequiresPermissions("groupcheck:list")
    @GetMapping("/ba-project-group-check/list")
    public String toPgCheckList() {
        return "pgcheck/list";
    }

    /**
     * 项目组审核页
     *
     * @auth admin
     * @date 2019-12-12
     * @param id
     * @return
     */
    @RequiresPermissions("groupcheck:check")
    @GetMapping("/ba-project-group-check/check/{id}")
    public String pgCheckPage(@PathVariable Integer id, Model model) {
        model.addAttribute("projectGroupCheckVo", this.baProjectGroupCheckService.getPgCheckById(id));
        model.addAttribute("checkStatusList",this.sysDataDicService.getDataDicSelectByParentCode(Const.CHECK_RESULT_CODE));
        SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("customerList", this.baCustomerService.getCustomerByChief(user.getId()));
        return "pgcheck/check";
    }

    /**
     * @dedscription 项目组审核：详情
     *
     * @auth admin
     * @date 2019-12-13
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("groupcheck:detail")
    @GetMapping("/ba-project-group-check/{id}")
    public String pgCheckDetail(@PathVariable(value="id") Integer id, Model model) {
        ProjectGroupCheckVo pgcv = this.baProjectGroupCheckService.getPgCheckById(id);
        BaProjectGroup bpg = this.baProjectGroupService.getById(pgcv.getPgId());
        model.addAttribute("projectGroupCheckVo", pgcv);
        model.addAttribute("customer",bpg.getCustomerId() == null ? "" : this.baCustomerService.getById(bpg.getCustomerId()));
        return "pgcheck/detail";
    }

    ////////////////////////////////////////////////////////////////////////////////////////项目组审核 end /////////////////////////////////////////////////////////////////////////

    /**
     * @description 申请修改审核列表
     *
     * @auth admin
     * @date 2019-12-24
     * @return
     */
    @RequiresPermissions("empApplyCheck:list")
    @GetMapping("/ba-emp-apply-check/list")
    public String toApplyCheckPageList() {
        return "applycheck/list";
    }

    /**
     * @description 申请修改记录列表
     *
     * @auth admin
     * @date 2019-12-27
     * @return
     */
    @RequiresPermissions("applyEmployee:list")
    @GetMapping("/ba-apply-employee/list")
    public String toEmpApplyPageList(Model model) {
        // 审核状态
        model.addAttribute("checkStatusList",this.sysDataDicService.getDataDicSelectByParentCode(Const.CHECK_STATUS_PARENT_CODE));
        return "applyemp/list";
    }

    /**
     * @description 申请修改 审核
     *
     * @auth admin
     * @date 2019-12-25
     * @param id
     * @param mode     e: 员工基本信息   w: 考勤信息
     * @param model
     * @return
     */
    @RequiresPermissions("empApplyCheck:check")
    @GetMapping("/ba-emp-apply-check/check/{id}/{mode}")
    public String toApplyCheck(@PathVariable(value="id") Integer id, @PathVariable(value="mode") String mode, Model model) {
        model.addAttribute("checkStatusList",this.sysDataDicService.getDataDicSelectByParentCode(Const.CHECK_RESULT_CODE));
        model.addAttribute("id",id);
        model.addAttribute("mode", mode);
        return "applycheck/check";
    }

    /**
     * 申请修改考勤详细页
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("applyCheck:workattendanceDetail")
    @GetMapping("/ba-apply-check/workattendance/detail/{id}")
    public String totoApplyWorkattendanceDetailPage(@PathVariable(value="id") Integer id,Model model){
        model.addAttribute("applyId", id);
       // model.addAttribute("mode","view");
        return "applycheck/attend_detail";
    }
    /**
     * @description 员工申请审核:详细
     *
     * @auth admin
     * @date 2019-12-26
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("empApplyCheck:detail")
    @GetMapping("/ba-emp-apply-check/detail/{id}")
    public String toApplyEmpDetailPage(@PathVariable(value="id") Integer id, Model model) {
        // model.addAttribute("emps",this.baApplyEmployeeService.getApplyEmpListByApplyId(id));
        model.addAttribute("applyId", id);
        return "applycheck/detail";
    }

    /**
     * @description 员工申请修改：验证口令页
     *
     * @auth admin
     * @date 2019-12-27
     * @param id
     * @return
     */
    @RequiresPermissions("applyEmployee:verify")
    @GetMapping("/ba-apply-employee/verify/{id}")
    public String toVerifyApplyEmpPage(@PathVariable(value="id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "applyemp/verify";
    }

    /**
     * @description 员工申请：修改页
     *
     * @auth admin
     * @date 2019-12-27
     * @param applyId
     * @return
     */
    @RequiresPermissions("applyEmployee:empList")
    @GetMapping("/ba-apply-employee/emp-list/{applyId}")
    public String toApplyEmpPage(@PathVariable(value="applyId") Integer applyId, Model model) {
        model.addAttribute("applyId", applyId);
        return "applyemp/emp-list";
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////员工审核//////////////////////////////////////////////////////////////////

    /**
     * @description 员工离场
     *
     * @auth admin
     * @date 2019-12-30
     * @param applyEmpId
     * @return
     */
    @RequiresPermissions("empLeavePg:leavePg")
    @GetMapping("/ba-emp-leave-pg/{applyEmpId}")
    public String toEmpLeavePgPage(@PathVariable(value="applyEmpId") Integer applyEmpId, Model model) {
        BaApplyEmployee bae = baApplyEmployeeService.getById(applyEmpId);
       // BaEmpLeavePg belp = this.baEmpLeavePgService.getEmpLeavePgByApplyEmpId(applyEmpId);
        // 取得项目ID
        // Integer pgId = this.baEmpApplyCheckService.getById(applyId).getPgId();
        // 项目ID， 员工ID， 入场状态，取得pg_emp_id
        // Integer pgEmpId = this.baPgEmpService.getBaPgEmpByPgIdAndEmpId(pgId, empId, Const.ENTRANCE_STATUS_I).getId();
        BaEmployee be = this.baEmployeeService.getById(bae.getEmpId());
      //  model.addAttribute("empLeavePg", belp);
        model.addAttribute("applyEmpId", applyEmpId);
        model.addAttribute("emp", be);
        return "applyemp/leave-pg";
    }

    /**
     * @description 员工离职
     *
     * @auth admin
     * @date 2019-12-30
     * @param applyEmpId
     * @param model
     * @return
     */
    @RequiresPermissions("empLeaveJob:leaveJob")
    @GetMapping("/ba-emp-leave-job/{applyEmpId}")
    public String toEmpLeaveJobPage(@PathVariable(value="applyEmpId") Integer applyEmpId, Model model) {
        BaApplyEmployee bae = this.baApplyEmployeeService.getById(applyEmpId);
        BaEmployee be = this.baEmployeeService.getById(bae.getEmpId());
        // BaEmpLeaveJob belj = this.baEmpLeaveJobService.getEmpLeaveJobByApplyEmpId(applyEmpId);
        // model.addAttribute("empLeaveJob", belj);
        model.addAttribute("emp", be);
        model.addAttribute("applyEmpId", applyEmpId);
        return "applyemp/leave-job";
    }

    /**
     * @description 员工申请修改：编辑
     *
     * @auth admin
     * @date 2019-12-30
     * @param applyEmpId
     * @param model
     * @return
     */
    @RequiresPermissions("applyEmployee:edit")
    @GetMapping("/ba-apply-employee/edit/{applyEmpId}")
    public String toEditApplyEmpPage(@PathVariable(value="applyEmpId") Integer applyEmpId, Model model) {
        List<BaCity> cities = baCityService.getBaCityList();
        model.addAttribute("cities",cities);
        model.addAttribute("applyEmpId", applyEmpId);
        BaApplyEmployee bae = this.baApplyEmployeeService.getById(applyEmpId);
        Integer pgId = this.baEmpApplyCheckService.getById(bae.getApplyId()).getPgId();
        model.addAttribute("levelPriceList", this.baLevelPriceService.getLevelPriceByPgId(pgId));
        model.addAttribute("unitList", sysDataDicService.getDataDicSelectByParentCode(Const.UNIT_PARENT_CODE));
        model.addAttribute("emp",this.baEmployeeService.getProjectEmpById(bae.getEmpId()));
        return "applyemp/edit";
    }

}

