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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-19
 */
@Service
public class SysDataDicServiceImpl extends ServiceImpl<SysDataDicMapper, SysDataDic> implements SysDataDicService {

    @Autowired
    private SysDataDicMapper sysDataDicMapper;
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

       }
        return r;
    }

    @Override
    public Result updateDataDic(SysDataDic sysDataDic) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
            UpdateWrapper<SysDataDic> updateWrapper = new UpdateWrapper<SysDataDic>();
            updateWrapper.set("dic_code",sysDataDic.getDicCode());
            updateWrapper.set("dic_content",sysDataDic.getDicContent());
            updateWrapper.set("sort",sysDataDic.getSort());
            updateWrapper.set("pid",sysDataDic.getPid());
            Integer ret=sysDataDicMapper.update(sysDataDic,updateWrapper);
            if(ret!=null && ret>0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
       } catch (Exception e) {
            e.printStackTrace();

        }
        return r;

    }

    @Override
    public Result deleteDataDic(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<SysDataDic> updateWrapper = new UpdateWrapper<SysDataDic>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.set("id",id);
            SysDataDic sysDataDic = new SysDataDic();
            sysDataDic.setId(id);
            Integer ret=sysDataDicMapper.update(sysDataDic,updateWrapper);
            if(ret!=null && ret>0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return r;
    }

    @Override
    public Result updateDataDicStart(Integer id) {
        return null;
    }

    @Override
    public SysDataDic getDataDicStop(Integer id) {
        return null;
    }

    @Override
    public Result pidSelectData() {
        QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",Const.DATA_DIC_ROOT);
        queryWrapper.orderByAsc("sort");
        List<SysDataDic> sysDataDicList = sysDataDicMapper.selectList(queryWrapper);
        List<SelectVo> pidSelectVoList = new ArrayList<SelectVo>();
        pidSelectVoList.add(new SelectVo(1,"数据字典"));
        for(SysDataDic sysDataDic: sysDataDicList){
            SelectVo selectVo = new SelectVo(sysDataDic.getId(),sysDataDic.getDicContent());
            pidSelectVoList.add(selectVo);
        }
        Result r = new Result(HttpResultEnum.CODE_0.getCode(), HttpResultEnum.CODE_0.getMessage());
        r.setData(pidSelectVoList);
        return r;
    }

    @Override
    public PageData listPageData(DataDicCo dataDicCo) {
        Page<SysDataDicVo> page = new Page<SysDataDicVo>(dataDicCo.getPage(),dataDicCo.getLimit());
        List<SysDataDicVo> sysDataDicList = sysDataDicMapper.getDataDicVoByPid(Const.DATA_DIC_ROOT);
        List<SysDataDicVo> sysPageDataDicList = new ArrayList<>();
        for(SysDataDicVo sysDataDic: sysDataDicList){

            sysPageDataDicList.add(sysDataDic);
            List<SysDataDicVo> sysDataDicList2 = sysDataDicMapper.getDataDicVoByPid(sysDataDic.getId());
            for(SysDataDicVo sysDataDic2: sysDataDicList2){
                sysPageDataDicList.add(sysDataDic2);
            }
        }
        page.setRecords(sysPageDataDicList);
        page.setTotal(sysPageDataDicList.size());
        return new PageData(page.getTotal(),page.getRecords());
    }
}
