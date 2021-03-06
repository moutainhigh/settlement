package com.settlement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.CustomerCo;
import com.settlement.entity.*;
import com.settlement.mapper.*;
import com.settlement.service.BaCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.utils.Status;
import com.settlement.vo.BaApplyTransferVo;
import com.settlement.vo.BaCustomerAndProjectTreeVo;
import com.settlement.vo.BaCustomerAndProjectVo;
import com.settlement.vo.BaCustomerVo;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@Service
@Transactional
public class BaCustomerServiceImpl extends ServiceImpl<BaCustomerMapper, BaCustomer> implements BaCustomerService {
    @Autowired
    private BaDeptCustomerMapper baDeptCustomerMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private BaPgTimeParamMapper baPgTimeParamMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private BaApplyTransferMapper baApplyTransferMapper;
    @Autowired
    private BaTransferMapper baTransferMapper;
    /**
     * 加载列表页面
     * @param customerCo
     * @return
     */
    @Override
    public PageData listPageData(CustomerCo customerCo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("userId",user.getId());
        SysRole sysRole = sysRoleMapper.getSysRoleByUserId(map);
        customerCo.setUserId(user.getId());
        customerCo.setRoleCode(sysRole.getRoleCode());
        customerCo.setDelFlag(Const.DEL_FLAG_N);
        Page<BaCustomerVo> page = new Page<>(customerCo.getPage(),customerCo.getLimit());
        page.setRecords(this.baseMapper.listPageData(customerCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     * @param baCustomerVo
     * @return
     */
    @Override
    public Result add(BaCustomerVo baCustomerVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            baCustomerVo.setDelFlag(Const.DEL_FLAG_N);
            baCustomerVo.setEnabled(Const.ENABLED_Y);
            baCustomerVo.setCreateTime(new Date());
            Integer ret = this.baseMapper.insert(baCustomerVo);
            if (ret != null && ret > 0) {
                BaDeptCustomer baDeptCustomer = new BaDeptCustomer();
                baDeptCustomer.setDeptId(baCustomerVo.getDeptId());
                baDeptCustomer.setCustomerId(baCustomerVo.getId());
                Integer ret2 = baDeptCustomerMapper.insert(baDeptCustomer);
                if (ret2 != null && ret2 > 0) {
                    r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  r;

    }

    /**
     * 修改
     * @param baCustomerVo
     * @return
     */
    @Override
    public Result update(BaCustomerVo baCustomerVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
        UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",baCustomerVo.getId());
        Integer ret=this.baseMapper.update(baCustomerVo,updateWrapper);
        if(ret!=null && ret>0) {
            QueryWrapper<BaDeptCustomer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("customer_id",baCustomerVo.getId());
            BaDeptCustomer baDeptCustomer = baDeptCustomerMapper.selectOne(queryWrapper);
            Integer ret2 = 0;
            //查询关联表不存在新增一条
            if(baDeptCustomer==null) {
                BaDeptCustomer baDeptCustomer2 = new BaDeptCustomer();
                baDeptCustomer2.setCustomerId(baCustomerVo.getId());
                baDeptCustomer2.setDeptId(baCustomerVo.getDeptId());
                ret2 = baDeptCustomerMapper.insert(baDeptCustomer2);
            } else {
                baDeptCustomer.setDeptId(baCustomerVo.getDeptId());
                ret2 = baDeptCustomerMapper.updateById(baDeptCustomer);
            }

            if(ret2!=null && ret2>0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Result delete(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<BaCustomer>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            BaCustomer baExportParam = this.baseMapper.selectById(id);
            Integer ret=this.baseMapper.update(baExportParam,updateWrapper);
            if(ret!=null && ret>0) {
                QueryWrapper<BaDeptCustomer> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("customer_id",id);
                Integer ret2=baDeptCustomerMapper.delete(queryWrapper);
                if(ret2!=null && ret2>0) {
                    r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 启用状态
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStart(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("enabled", Const.ENABLED_Y);
            BaCustomer baCustomer = this.baseMapper.selectById(id);

            Integer ret = this.baseMapper.update(baCustomer, updateWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 停用状态
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStop(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        BaCustomer baCustomer = this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baCustomer,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 根据id查询得到BaCustomerVo
     * @param id
     * @return
     */
    @Override
    public BaCustomerVo getBaCustomerVoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("id",id);
        return this.baseMapper.getBaCustomerVoById(map);
    }

    /**
     * 根据用户id查询当前的客户信息和项目组
     * @author kun
     * @param userId
     * @return
     */
    @Override
    public List<BaCustomerAndProjectVo> getCustomerAndProjectByUserId(Integer userId) {
        Map<String,Object> roleMap = new HashMap<>();
        roleMap.put("delFlag",Const.DEL_FLAG_N);
        roleMap.put("userId",userId);
        SysRole sysRole = sysRoleMapper.getSysRoleByUserId(roleMap);

        Map<String,Object> map = new HashMap<>();
        map.put("enabled",Const.ENABLED_Y);
        map.put("userId",userId);
        map.put("roleCode",sysRole.getRoleCode());
        List<BaCustomerAndProjectVo> baCustomers = this.baseMapper.getCustomerAndProjectByUserId(map);
        return baCustomers;
    }

    /**
     * 根据用户id查询当前的客户信息和项目组生成树
     * @author kun
     * @param userId
     * @return
     */
    @Override
    public Object getCustomerAndProjectTreeByUserId(Integer userId) {
        JSONObject josn = new JSONObject();
        Status status = new Status();
        status.setCode(200);
        status.setMessage("操作成功");
        josn.put("status", JSONArray.toJSON(status));
        josn.put("data", JSONArray.toJSON(getCustomerAndProjectTreeListByUserId(userId)));
        return josn.toJSONString();
    }

    /**
     * 根据用户id查询当前的first node
     * @param userId
     * @return
     */
    @Override
    public Result getFirstNode(Integer userId){
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        String first = null;
        List<BaCustomerAndProjectTreeVo> baCustomerAndProjectTreeVos = getCustomerAndProjectTreeListByUserId(userId);
        for(BaCustomerAndProjectTreeVo ba:baCustomerAndProjectTreeVos) {
            if(ba.isLeaf()){
                first = ba.getId();
                break;
            }
        }
        if(first!=null) {
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setData(first);
        }
        return r;
    }
    /**
     * 根据用户id查询当前的客户信息和项目组list
     * @param userId
     * @return
     */
    @Override
    public List<BaCustomerAndProjectTreeVo> getCustomerAndProjectTreeListByUserId(Integer userId){
        /**
         * 1、用户id查询对应的角色
         * 2、客户经理可以查看所有的项目
         **/
        Map<String,Object> roleMap = new HashMap<>();
        roleMap.put("delFlag",Const.DEL_FLAG_N);
        roleMap.put("userId",userId);
        SysRole sysRole = sysRoleMapper.getSysRoleByUserId(roleMap);

        Map<String,Object> mapkey = new HashMap<>();
        mapkey.put("enabled",Const.ENABLED_Y);
        mapkey.put("checkStatus",Const.CHECK_STATUS_CHECK_PASS);
        mapkey.put("userId",userId);
        mapkey.put("roleCode",sysRole.getRoleCode());
        List<BaCustomerAndProjectVo> baCustomerAndProjectVos = this.baseMapper.getCustomerAndProjectByUserId(mapkey);
        //存储判断子结点
        Map<String,BaCustomerAndProjectVo> map = new HashMap<String,BaCustomerAndProjectVo>();
        //存储判断父结点
        Map<String,BaCustomerAndProjectTreeVo> projectMap = new HashMap<String,BaCustomerAndProjectTreeVo>();
        //返回树结构数据
        List<BaCustomerAndProjectTreeVo> baCustomerAndProjectTreeVos = new ArrayList<>();
        for(BaCustomerAndProjectVo ba: baCustomerAndProjectVos) {
            //判断当前是否已经存在父结点
            if(map.containsKey(ba.getCode())) {
                //判断当前是否已经存在相同子结点,存在跳出本次循环
                String projectCode = ba.getProjectCode();
                if(!projectMap.containsKey(projectCode)) {
                    //不存在相同子结点,构造数据添加
                    BaCustomerAndProjectTreeVo baVo = new BaCustomerAndProjectTreeVo();
                    baVo.setId(ba.getProjectId()+"");
                    baVo.setProjectCode(projectCode);
                    baVo.setTitle(ba.getProjectName());
                    baVo.setParentId(ba.getId()+ba.getCustomerName().hashCode()+"");
                    baVo.setCheckArr("0");
                    baVo.setLeaf(true);
                    baCustomerAndProjectTreeVos.add(baVo);
                    projectMap.put(projectCode,baVo);
                }

            } else {
                //不存在父结点 构造父结点并添加到map中
                map.put(ba.getCode(),ba);
                //构建父结点
                BaCustomerAndProjectTreeVo baVo = new BaCustomerAndProjectTreeVo();
                String baVoId = ba.getId()+ba.getCustomerName().hashCode()+"";
                baVo.setId(baVoId);
                baVo.setTitle(ba.getCustomerName());
                baVo.setParentId("0");
                baVo.setCheckArr("0");
                baCustomerAndProjectTreeVos.add(baVo);
                //同时构造子结点
                BaCustomerAndProjectTreeVo baChildVo = new BaCustomerAndProjectTreeVo();
                baChildVo.setId(ba.getProjectId()+"");
                baChildVo.setProjectCode(ba.getProjectCode());
                baChildVo.setTitle(ba.getProjectName());
                baChildVo.setParentId(baVo.getId());
                baChildVo.setCheckArr("0");
                baChildVo.setLeaf(true);
                if(!projectMap.containsKey(baChildVo.getProjectCode())){
                    baCustomerAndProjectTreeVos.add(baChildVo);
                    projectMap.put(baChildVo.getProjectCode(),baChildVo);
                }
            }
        }
        return baCustomerAndProjectTreeVos;
    }
    @Override
    public List<BaCustomerVo> getCustomerByDeptId(Integer deptId) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deptId",deptId);
        paramMap.put("enabled", Const.ENABLED_Y);
        return this.baseMapper.getBaCustomerByDeptId(paramMap);
    }

    @Override
    public List<BaCustomerVo> getCustomerByChief(Integer chief) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("chief",chief);
        paramMap.put("enabled", Const.ENABLED_Y);
        return this.baseMapper.getBaCustomerByChief(paramMap);
    }

    /**
     * 客户移交
     * @param baApplyTransferVo
     * @return
     */
    @Override
    public Result customerApplyTransfer(BaApplyTransferVo baApplyTransferVo) {
        Result r = new Result(HttpResultEnum.TRANS_CODE_500.getCode(),HttpResultEnum.TRANS_CODE_500.getMessage());
        try{
            baApplyTransferVo.setCheckStatus(Const.CHECK_STATUS_NO_CHECK);
            baApplyTransferVo.setApplyTime(new Date());
            baApplyTransferVo.setApplyType(Const.APPLY_TRANSFER_CUSTOMER);
            baApplyTransferVo.setCheckUser(1);
            Integer ret = baApplyTransferMapper.insert(baApplyTransferVo);
            if(ret!=null && ret >0) {
                List<BaTransfer> baTransfers = new ArrayList<>();
                for(Integer id : baApplyTransferVo.getIds()) {
                    BaTransfer baTransfer = new BaTransfer();
                    baTransfer.setApplyTransferId(baApplyTransferVo.getId());
                    baTransfer.setTransferId(id);
                    baTransfers.add(baTransfer);
                }
                Integer ret2 = baTransferMapper.insertBatch(baTransfers);
                if(ret2!=null && ret2 >0) {
                    r.setMsg(HttpResultEnum.TRANS_CODE_200.getMessage());
                    r.setCode(HttpResultEnum.TRANS_CODE_200.getCode());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }
}
