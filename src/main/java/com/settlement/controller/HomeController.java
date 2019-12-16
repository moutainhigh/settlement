package com.settlement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.*;
import com.settlement.service.*;
import com.settlement.utils.Const;
import com.settlement.utils.Result;
import com.settlement.vo.*;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
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
        SysUser user = this.sysUserService.getOne(new QueryWrapper<SysUser>().eq("id", id));
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
        // 根据数据字典 取得停用原因
        model.addAttribute("userVo",sysUserService.getUserStop(id));
        return "user/stop";
    }


/////////////////////////////////////////////////////////////////////////////////   用户跳转页面 end //////////////////////////////////////////////////////////////

    @GetMapping("/sys-role/list")
    public String toRolePageList() {
        return "role/list";
    }

    /**
     * 跳转角色添加页面
     * @return
     */
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
     * 菜单管理列表
     * @return
     */
    @GetMapping("/sys-permission/list")
    public String toPermissionList() {
        //public String toPermissionList(@RequestParam(defaultValue = "update") String mode, Model model) {
        //model.addAttribute("mode",mode);
        return "permission/list";
    }

    /**
     * 添加菜单
     * @param id
     * @param mode
     * @param model
     * @return
     */
    @GetMapping("/sys-permission/iframeContent")
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
            SysPermission sysPermission=sysPermissionService.getRootSysPermissionVoById(Const.PERMISSION_ROOT_ID);
            model.addAttribute("mode","default");
            model.addAttribute("sysPermission",sysPermission);
        }
        return "permission/iframeContent";
    }

    /**
     * 跳转到部门列表页
     * @return
     */
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
    @GetMapping("/sys-dept/iframeContent")
    public String deptIframeContent(@RequestParam(required =false,defaultValue = "1") Integer id, @RequestParam(required = false,defaultValue = "") String mode, Model model) {
        List<SysRoleVo> sysRoleVos = sysRoleService.getRoleVo();
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
            SysDeptVo sysDept=sysDeptService.getRootSysDeptVoById(Const.DEPT_ROOT);
            sysRoleVos = sysRoleService.getRoleVo();
            model.addAttribute("sysRoleVos",sysRoleVos);
            model.addAttribute("mode","default");
            model.addAttribute("sysDept",sysDept);
        }
        model.addAttribute("sysRoleVos",sysRoleVos);
        return "dept/iframeContent";
    }

/////////////////////////////////////////////////////////////////////////////////   用户跳转页面 end //////////////////////////////////////////////////////////////

    /**
     * 数据字典列表
     * @return
     */
    @GetMapping("/sys-data-dic/list")
    public String toDatatDicList() {
        return "dic/list";
    }

    /**
     *@desciption 数据字典添加页面
     *
     * @return
     */
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
    @GetMapping("/ba-project-group/add")
    public String toPgAdd() {
        return "pg/add";
    }

    /**
     * @description 项目组编辑
     *
     * @auth admin
     * @date 2019-11-25
     * @return
     */
    @GetMapping("/ba-project-group/edit/{id}")
    public String toPgEdit(@PathVariable Integer id, Model model) {
        model.addAttribute("pg",this.baProjectGroupService.getById(id));
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
    @GetMapping("/ba-project-group/relate-settlement/{id}")
    public String toRelateSettlement(@PathVariable Integer id, Model model) {
        model.addAttribute("pg", this.baProjectGroupService.getProjectGroupSettlementById(id));
        return "pg/relate-settlement";
    }
    /////////////////////////////////////////////////////////////  项目组跳转end  ///////////////////////////////////////////////////////////////////

    /**
     * 跳转时间点参数页面
     * @return
     */
    @GetMapping("/ba-time-param/list")
    public String toTimeParam(){
        return "timeparam/list";
    }

    /**
     * 跳转角色添加页面
     * @return
     */
    @GetMapping("/ba-time-param/toAddorUpdate/{mode}/{id}")
    public String toTimeParamAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            BaTimeParam baTimeParam = baTimeParamService.getById(id);
            model.addAttribute("baTimeParam",baTimeParam);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "timeparam/add";
    }

    /**
     * 跳转导出参数页面
     * @return
     */
    @GetMapping("/ba-export-param/list")
    public String toExportParam(){
        return "exportparam/list";
    }


    /**
     * 跳转角色添加页面
     * @return
     */
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


    /**
     * 跳转结算公式参数页面
     * @return
     */
    @GetMapping("/ba-formula-param/list")
    public String toFormulaParam(){
        return "formulaparam/list";
    }


    /**
     * 跳转角色添加页面
     * @return
     */
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

    /**
     * 跳转结算公式参数页面
     * @return
     */
    @GetMapping("/ba-customer/list")
    public String toBaCustomer(){
        return "customer/list";
    }


    /**
     * 跳转角色添加页面
     * @return
     */
    @GetMapping("/ba-customer/toAddorUpdate/{mode}/{id}")
    public String toBaCustomerAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
            BaCustomerVo baCustomerVo = new BaCustomerVo();
            model.addAttribute("baCustomer",baCustomerVo);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            BaCustomerVo baCustomerVo = baCustomerService.getBaCustomerVoById(id);
            model.addAttribute("baCustomer",baCustomerVo);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "customer/add";
    }
    /**
     * @description 员工录入
     *
     * @auth admin
     * @date 2019-11-28
     * @param pgId
     * @return
     */
    @GetMapping("/ba-project-employee/enter-emp/{pgId}")
    public String toEnterEmployPage(@PathVariable  Integer pgId, Model model) {
        model.addAttribute("pgId", pgId);
        return "emp/enter";
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
    @GetMapping("/ba-project-employee/add/{pgId}")
    public String toAddEmployeePage(@PathVariable Integer pgId, Model model) {
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
    @GetMapping("/ba-project-employee/edit/{id}/{pgId}")
    public String toEditEmployee(@PathVariable Integer id, @PathVariable Integer pgId, Model model) {
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
    @GetMapping("ba-project-employee/view/{id}")
    public String toViewImg(@PathVariable Integer id, Model model) {
        model.addAttribute("imgSrc", this.baEmployeeService.getProjectEmpById(id).getRateEmailFilename());
        return "emp/view";
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 跳转合同页面
     * @return
     */
    @GetMapping("/ba-contract/list")
    public String toBaContract(){
        return "contract/list";
    }


    /**
     * 跳转合同添加页面
     * @return
     */
    @GetMapping("/ba-contract/toAddorUpdate/{mode}/{id}")
    public String toBaContractAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {

        List<BaCustomer> baCustomers = baCustomerService.list();
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
            BaContractVo baContract = new BaContractVo();
            model.addAttribute("baContract",baContract);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            BaContractVo baContractVo = baContractService.getBaContractVoById(id);
            List<BaProjectGroup> baProjectGroups = baProjectGroupService.getGroupsByCustomerId(baContractVo.getCustomerId());
            model.addAttribute("baProjectGroups",baProjectGroups);
            model.addAttribute("baContract",baContractVo);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        model.addAttribute("baCustomers",baCustomers);
        return "contract/add";
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 考勤管理页面
     * @return
     */
    @GetMapping("/ba-work-attendance/list")
    public String toWorkAttendance(){
        return "workattendance/list";
    }
    /**
     * 考勤管理-编辑考勤信息
     * @param projectId 项目id
     * @return
     */
    @GetMapping("/ba-work-attendance/attendlist")
    public String toAttendList(@RequestParam(required = false) Integer projectId,Model model){

        if(projectId!=null) {
            BaProjectGroup baProjectGroup = baProjectGroupService.getById(projectId);
            BaProjectGroupAssistant baProjectGroupAssistant = baProjectGroupAssistantService.getBaProjectGroupAssistantByGroupId(projectId);
            model.addAttribute("checkUserId", baProjectGroupAssistant.getAssistantId());
        }
        List<String> years = baTimeParamService.getTimeYearValue();
        List<String> months = baTimeParamService.getTimeMonthValue();

        String stopTime =baTimeParamService.getStopTimeParam();
        String compelteTime = baTimeParamService.getCompleteParam();

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
    @GetMapping("/ba-work-attendance/applymodify/{checkUserId}/{ids}")
    public String toApplymodifyPage(@PathVariable Integer checkUserId,@PathVariable Integer[] ids,Model model, HttpSession session){
        model.addAttribute("workAttendanceIds",ids);
        //申请人
        SysUser applyUser = (SysUser)session.getAttribute("user");
        //审核人AM
        SysUser checkUser= sysUserService.getById(checkUserId);
        ApplyAndCheckUserVo applyAndCheckUserVo = new ApplyAndCheckUserVo(applyUser.getId(),checkUser.getId(),checkUser.getRealName(),ids);
        model.addAttribute("applyAndCheckUserVo",applyAndCheckUserVo);
        return "workattendance/applymodify";
    }

    /**
     * 考勤管理-编辑员工考勤
     * @param id 考勤id
     * @param model
     * @return
     */
    @GetMapping("/ba-work-attendance/edit/{id}")
    public String toWorkAttendanceEdit(@PathVariable Integer id, Model model){
        BaWorkAttendanceVo baWorkAttendanceVo = baWorkAttendanceService.getBaWorkAttendanceVoById(id);
        model.addAttribute("baWork",baWorkAttendanceVo);
        return "workattendance/add";
    }

    /**
     * 考勤申请修改记录-通过审核-修改考勤信息页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/ba-work-attendance/apply/workattendancelist/{id}")
   public String toApplyWorkattendanceList(@PathVariable Integer id, Model model) {
        model.addAttribute("applyId",id);
        return "apply/applyattendlist";
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 申请修改记录
     * @return
     */
    @GetMapping("/ba-apply/list")
    public String toApplyList(Model model){
        List<SysDataDic> checkStatusList = sysDataDicService.getCheckStatus();
        model.addAttribute("checkStatusList",checkStatusList);
        return "apply/list";
    }

    /**
     * 申请修改考勤记录
     * @param id
     * @return
     */
    @GetMapping("/ba-apply/workattendance/{id}")
    public String toApplyWorkAttendanceList(@PathVariable Integer id) {
        return "apply/attendlist";
    }

    /**
     * 跳转口令验证页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/ba-apply/passcode/{id}")
    public String toApplyPassCode(@PathVariable Integer id,Model model){
        BaApply baApply = baApplyService.getById(id);
        model.addAttribute("baApply",baApply);
        return "apply/passcode";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @description 项目组审核列表页
     *
     * @auth admin
     * @date 2019-12-10
     * @return
     */
    @GetMapping("/ba-project-group-check/list")
    public String toPgCheckList() {
        return "pgcheck/list";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

