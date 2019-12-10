package com.settlement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.CustomerCo;
import com.settlement.entity.BaCustomer;
import com.settlement.entity.BaDeptCustomer;
import com.settlement.mapper.BaCustomerMapper;
import com.settlement.mapper.BaDeptCustomerMapper;
import com.settlement.mapper.SysRoleMapper;
import com.settlement.service.BaCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.service.BaDeptCustomerService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.utils.Status;
import com.settlement.vo.BaCustomerAndProjectTreeVo;
import com.settlement.vo.BaCustomerAndProjectVo;
import com.settlement.vo.BaCustomerVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.management.BadAttributeValueExpException;
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
    /**
     * 加载列表页面
     * @param customerCo
     * @return
     */
    @Override
    public PageData listPageData(CustomerCo customerCo) {
        Page<BaCustomer> page = new Page<>(customerCo.getPage(),customerCo.getLimit());
        QueryWrapper<BaCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
        queryWrapper.like(StringUtils.isNotBlank(customerCo.getKeyword()),"param_name",customerCo.getKeyword());
        this.baseMapper.selectPage(page,queryWrapper);

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
        Map<String,Object> map = new HashMap<>();
        map.put("enabled",Const.ENABLED_Y);
        map.put("userId",userId);
        return this.baseMapper.getCustomerAndProjectByUserId(map);
    }

    /**
     * 根据用户id查询当前的客户信息和项目组生成树
     * @author kun
     * @param userId
     * @return
     */
    @Override
    public Object getCustomerAndProjectTreeByUserId(Integer userId) {
        /**
         * 1、用户id查询对应的角色
         * 2、客户经理可以查看所有的项目
         **/
        Map<String,Object> mapkey = new HashMap<>();
        mapkey.put("enabled",Const.ENABLED_Y);
        mapkey.put("userId",userId);
        List<BaCustomerAndProjectVo> baCustomerAndProjectVos = this.baseMapper.getCustomerAndProjectByUserId(mapkey);
        Map<String,BaCustomerAndProjectVo> map = new HashMap<String,BaCustomerAndProjectVo>();
        List<BaCustomerAndProjectTreeVo> baCustomerAndProjectTreeVos = new ArrayList<>();
        for(BaCustomerAndProjectVo ba: baCustomerAndProjectVos) {
            if(map.containsKey(ba.getCode())) {
                BaCustomerAndProjectTreeVo baVo = new BaCustomerAndProjectTreeVo();
                baVo.setId(ba.getProjectId());
                baVo.setTitle(ba.getProjectName());
                baVo.setParentId(ba.getId());
                baVo.setCheckArr("0");
                baCustomerAndProjectTreeVos.add(baVo);
            } else {
                map.put(ba.getCode(),ba);
                BaCustomerAndProjectTreeVo baVo = new BaCustomerAndProjectTreeVo();
                baVo.setId(ba.getId());
                baVo.setTitle(ba.getCustomerName());
                baVo.setParentId(0);
                baVo.setCheckArr("0");
                baCustomerAndProjectTreeVos.add(baVo);

                BaCustomerAndProjectTreeVo baChildVo = new BaCustomerAndProjectTreeVo();
                baChildVo.setId(ba.getProjectId());
                baChildVo.setTitle(ba.getProjectName());
                baChildVo.setParentId(ba.getId());
                baChildVo.setCheckArr("0");
                baCustomerAndProjectTreeVos.add(baChildVo);
            }
        }

        JSONObject josn = new JSONObject();
        Status status = new Status();
        status.setCode(200);
        status.setMessage("操作成功");
        josn.put("status", JSONArray.toJSON(status));
        josn.put("data", JSONArray.toJSON(baCustomerAndProjectTreeVos));
        return josn.toJSONString();
    }
}
