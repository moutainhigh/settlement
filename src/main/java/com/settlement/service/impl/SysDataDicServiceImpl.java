package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.DataDicCo;
import com.settlement.entity.SysDataDic;
import com.settlement.mapper.SysDataDicMapper;
import com.settlement.service.SysDataDicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SelectVo;
import com.settlement.vo.SysDataDicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-19
 */
@Service
@Transactional
public class SysDataDicServiceImpl extends ServiceImpl<SysDataDicMapper, SysDataDic> implements SysDataDicService {

    @Autowired
    private SysDataDicMapper sysDataDicMapper;

    /**
     * 添加数据字典
     * @param sysDataDic
     * @return
     */
    @Override
    public Result saveDataDic(SysDataDic sysDataDic) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
       try{
           Integer ret = sysDataDicMapper.insert(sysDataDic);
           if(ret!=null && ret>0) {
               r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
               r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
           }

       }catch (Exception e) {
           e.printStackTrace();
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
       }
        return r;
    }

    /**
     * 更新数据字典
     * @param sysDataDic
     * @return
     */
    @Override
    public Result updateDataDic(SysDataDic sysDataDic) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
            UpdateWrapper<SysDataDic> updateWrapper = new UpdateWrapper<SysDataDic>();
            updateWrapper.set("dic_code",sysDataDic.getDicCode());
            updateWrapper.set("dic_content",sysDataDic.getDicContent());
            updateWrapper.set("sort",sysDataDic.getSort());
            updateWrapper.set("pid",sysDataDic.getPid());
            updateWrapper.set("del_flag",sysDataDic.getDelFlag());
            Integer ret=sysDataDicMapper.update(sysDataDic,updateWrapper);
            if(ret!=null && ret>0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
       } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;

    }

    /**
     * 逻辑删除数据字典
     * @param id
     * @return
     */
    @Override
    public Result deleteDataDic(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<SysDataDic> updateWrapper = new UpdateWrapper<SysDataDic>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            SysDataDic sysDataDic = new SysDataDic();
            sysDataDic.setId(id);
            Integer ret=sysDataDicMapper.update(sysDataDic,updateWrapper);
            if(ret!=null && ret>0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 启用数据字典
     * @param id
     * @return
     */
    @Override
    public Result updateDataDicStart(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<SysDataDic> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_Y);
        SysDataDic sysDataDic = new SysDataDic();
        sysDataDic.setId(id);

        Integer ret = sysDataDicMapper.update(sysDataDic,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;

    }

    /**
     * 信用数据字典
     * @param id
     * @return
     */
    @Override
    public Result updateDataDicStop(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<SysDataDic> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        SysDataDic sysDataDic = new SysDataDic();
        sysDataDic.setId(id);

        Integer ret = sysDataDicMapper.update(sysDataDic,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 数据字典下拉选择框内容
     * @return
     */
    @Override
    public Result pidSelectData() {
        QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",Const.DATA_DIC_ROOT);
        queryWrapper.orderByAsc("sort");
        List<SysDataDic> sysDataDicList = sysDataDicMapper.selectList(queryWrapper);
        List<SelectVo> pidSelectVoList = new ArrayList<SelectVo>();
        pidSelectVoList.add(new SelectVo(0,"数据字典"));
        for(SysDataDic sysDataDic: sysDataDicList){
            SelectVo selectVo = new SelectVo(sysDataDic.getId(),sysDataDic.getDicContent());
            pidSelectVoList.add(selectVo);
        }
        Result r = new Result(HttpResultEnum.CODE_0.getCode(), HttpResultEnum.CODE_0.getMessage());
        r.setData(pidSelectVoList);
        return r;
    }

    /**
     * 加载显示数据字典页面
     * @param dataDicCo
     * @return
     */
    @Override
    public PageData listPageData(DataDicCo dataDicCo) {
        Page<SysDataDicVo> page = new Page<SysDataDicVo>(dataDicCo.getPage(),dataDicCo.getLimit());
        dataDicCo.setPid(Const.DATA_DIC_ROOT);
        List<SysDataDicVo> sysDataDicList = sysDataDicMapper.getDataDicVoByPid(dataDicCo,page);
        List<SysDataDicVo> sysPageDataDicList = new ArrayList<>();
        for(SysDataDicVo sysDataDic: sysDataDicList){

            sysPageDataDicList.add(sysDataDic);
            dataDicCo.setPid(sysDataDic.getDicCode());
            List<SysDataDicVo> sysDataDicList2 = sysDataDicMapper.getDataDicVoByPid(dataDicCo,page);

            for(SysDataDicVo sysDataDic2: sysDataDicList2){
                sysPageDataDicList.add(sysDataDic2);
            }
        }
        page.setRecords(sysPageDataDicList);
        page.setTotal(sysPageDataDicList.size());
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     *  更新数据字典的状态
     * @param id
     * @param enable
     * @return
     */
    @Override
    public Result updateStatus(Integer id,boolean enable) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<SysDataDic> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        SysDataDic sysDataDic = new SysDataDic();
        sysDataDic.setId(id);
        if(enable) {
            updateWrapper.set("enabled", Const.ENABLED_Y);
        } else{
            updateWrapper.set("enabled", Const.ENABLED_N);
        }

        Integer ret = sysDataDicMapper.update(sysDataDic,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
    return r;
    }

    /**
     * 检查dicCode是否可用
     * @param dicCode
     * @return
     */
    @Override
    public Result dicCodeIsExist(String dicCode) {
        Result r = null;
        QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dic_code",dicCode);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        Integer count = this.sysDataDicMapper.selectCount(queryWrapper);
        if(count!=null && count>0) {
            r = new Result(HttpResultEnum.DIC_CODE_1.getCode(),HttpResultEnum.DIC_CODE_1.getMessage());
        } else {
            r = new Result(HttpResultEnum.DIC_CODE_0.getCode(),HttpResultEnum.DIC_CODE_0.getMessage());
        }
        return r;
    }

    @Override
    public List<SysDataDicVo> getDataDicSelectByParentCode(String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code",code);
        map.put("enabled",Const.ENABLED_Y);
        return this.baseMapper.getDataDicSelectByParentCode(map);
    }

    @Override
    public SysDataDic getRoot(String rootCode) {
        return  this.baseMapper.getRoot(rootCode);
    }
}
