package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.TimeParamCo;
import com.settlement.entity.BaPgTimeParam;
import com.settlement.entity.BaProjectGroup;
import com.settlement.entity.BaTimeParam;
import com.settlement.mapper.BaPgTimeParamMapper;
import com.settlement.mapper.BaProjectGroupMapper;
import com.settlement.mapper.BaTimeParamMapper;
import com.settlement.service.BaTimeParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.BaTimeParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 时间点参数表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-27
 */
@Service
@Transactional
public class BaTimeParamServiceImpl extends ServiceImpl<BaTimeParamMapper, BaTimeParam> implements BaTimeParamService {

    @Autowired
    private BaPgTimeParamMapper baPgTimeParamMapper;
    @Autowired
    private BaProjectGroupMapper baProjectGroupMapper;
    /**
     * 加载列表页面
     * @param timeParamCo
     * @return
     */
    @Override
    public PageData listPageData(TimeParamCo timeParamCo) {
        timeParamCo.setDelFlag(Const.DEL_FLAG_N);
        Page<BaTimeParamVo> page = new Page<>(timeParamCo.getPage(),timeParamCo.getLimit());
        page.setRecords(this.baseMapper.listPageData(timeParamCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     *  添加
     * @param baTimeParamVo
     * @return
     */
    @Override
    public Result addTimeParam(BaTimeParamVo baTimeParamVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        BaTimeParam baTimeParam = new BaTimeParam();
        baTimeParam.setParamCode(baTimeParamVo.getParamCode());
        baTimeParam.setParamItem(baTimeParamVo.getParamItem());
        baTimeParam.setParamValue(baTimeParamVo.getParamValue());
        baTimeParam.setType(baTimeParamVo.getType());
        baTimeParam.setEnabled(Const.ENABLED_Y);
        baTimeParam.setCreateUserId(baTimeParamVo.getCreateUserId());
        baTimeParam.setCreateTime(new Date());
        baTimeParam.setDelFlag(Const.DEL_FLAG_N);
        try {
            Integer ret = this.baseMapper.insert(baTimeParam);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  r;
    }

    /**
     * 检查有没有保存过时间点参数
     * @param projectIds
     * @param timeParamId
     * @return
     */
    @Override
    public Result checkTimeParamStatusByProjectIds(Integer[] projectIds, String timeParamId,String type) {
        Result r =  new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        Map<Integer,List<BaTimeParamVo>> map = new HashMap<>();
        for(Integer projectId:projectIds) {
            //查询是否存在同种时间点的数据
            Map<String,Object> map2 = new HashMap<>();
            map2.put("type",type);
            map2.put("projectId",projectId);
            map2.put("enabled",Const.ENABLED_Y);
            List<BaTimeParamVo> baPgTimeParams = this.baseMapper.getTimeParamVoByProjectIdAndType(map2);
            if(baPgTimeParams.size()>1) {
                //存在已经保存过的时间点
                map.put(projectId,baPgTimeParams);
            }
        }
        List<String> existProjectId = new ArrayList<>();
        //取出项目保存过的时间点
        for(Map.Entry<Integer,List<BaTimeParamVo>> entry: map.entrySet()) {
            Integer projectId = entry.getKey();
            for(BaTimeParamVo baTimeParamVo:entry.getValue()) {
                Integer projectId2 = baTimeParamVo.getProjectId();
                String paramItem = baTimeParamVo.getParamItem();
                Integer timeParamId2 = baTimeParamVo.getId();
//                if(entry.getKey().equals(baTimeParamVo.getProjectId())) {
                if(projectId.equals(projectId2) && !(timeParamId2.equals(timeParamId))) {
                   BaProjectGroup baProjectGroup = baProjectGroupMapper.selectById(projectId);
                    existProjectId.add(baProjectGroup.getPgName()+"已经保存了:【 "+paramItem+"】时间点");
                }
            }
        }
        if(existProjectId.size()>0) {
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setData(existProjectId);
        }
        return  r;
    }
    /**
     * 保存项目的时间点参数
     * @param projectIds
     * @param timeParamId
     * @return
     */
    @Override
    public Result saveRelateProject(Integer[] projectIds, String timeParamId) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        //先删除关联表数据
        QueryWrapper<BaPgTimeParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t_p_id",timeParamId);
        this.baPgTimeParamMapper.delete(queryWrapper);
        //添加关联表数据
        List<BaPgTimeParam> baPgTimeParams = new ArrayList<>();
        for(Integer projectId:projectIds) {
            BaPgTimeParam baPgTimeParam = new BaPgTimeParam();
            baPgTimeParam.setPgId(projectId);
            baPgTimeParam.setTPId(Integer.parseInt(timeParamId));
            baPgTimeParams.add(baPgTimeParam);
        }
        Integer ret = baPgTimeParamMapper.saveBatchs(baPgTimeParams);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
            r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
        }
        return r;
    }
    /**
     * 修改
     * @param baTimeParamVo
     * @return
     */
    @Override
    public Result updateTimeParam(BaTimeParamVo baTimeParamVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        BaTimeParam baTimeParam = this.baseMapper.selectById(baTimeParamVo.getId());
        UpdateWrapper<BaTimeParam> updateWrapper = new UpdateWrapper<>();
        baTimeParam.setParamCode(baTimeParamVo.getParamCode());
        baTimeParam.setParamItem(baTimeParamVo.getParamItem());
        baTimeParam.setParamValue(baTimeParamVo.getParamValue());
        baTimeParam.setType(baTimeParamVo.getType());
        updateWrapper.eq("id",baTimeParamVo.getId());
        try {
            Integer ret = this.baseMapper.update(baTimeParam, updateWrapper);
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
     * 删除
     * @param id
     * @return
     */
    @Override
    public Result deleteTimeParam(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<BaTimeParam> updateWrapper = new UpdateWrapper<BaTimeParam>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            BaTimeParam baTimeParam = new BaTimeParam();
            baTimeParam.setId(id);
            Integer ret=this.baseMapper.update(baTimeParam,updateWrapper);
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
     * 启用
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStart(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaTimeParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_Y);
        BaTimeParam baTimeParam =  this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baTimeParam,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStop(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaTimeParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        BaTimeParam baTimeParam =  this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baTimeParam,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 检查是否存在 param_code
     * @param code
     * @return
     */
    @Override
    public Result isExist(String code) {
        Result r = null;
        QueryWrapper<BaTimeParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("param_code",code);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if(count!=null && count>0) {
            r = new Result(HttpResultEnum.TIME_PARAM_CODE_1.getCode(),HttpResultEnum.TIME_PARAM_CODE_1.getMessage());
        } else {
            r = new Result(HttpResultEnum.TIME_PARAM_CODE_0.getCode(),HttpResultEnum.TIME_PARAM_CODE_0.getMessage());
        }
        return r;
    }

    /**
     * 获得当前年份及上个月和当前月份
     * @return
     */
    @Override
    public List<String> getTimeYearValue() {
        List<String> years = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        years.add(year);
        return years;

    }

    @Override
    public List<String> getTimeMonthValue() {
        List<String> months = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        String currentMonth=String.valueOf(cal.get(Calendar.MONTH)+1);
        String lastMonth=String.valueOf(cal.get(Calendar.MONTH));
        months.add(currentMonth);
        months.add(lastMonth);
        return months;

    }

    /**
     * 获得结算考勤时间停止点
     * @return
     */
    @Override
    public String getStopTimeParam(Integer projectId) {
        return  getTimeParamValue(projectId,Const.TIME_PRAMA_STOP);
    }

    /**
     * 获得结算考勤时间完成点
     * @return
     */
    @Override
    public String getCompleteParam(Integer projectId) {
        return  getTimeParamValue(projectId,Const.TIME_PRAMA_COMPELETE);
    }

    /**
     * 考勤结算时间点开始-完成区间  -1 小于 0 等于 1 大于
     * @return
     */
    @Override
    public Result judgeWorkattendanceDate(Integer projectId) {
        Result r = new Result();
        String completeTimeParam = getCompleteParam(projectId);
        String stopTimeParam = getStopTimeParam(projectId);
        int compareCompleteValue = getCompareDateValue(completeTimeParam);
        int compareStopValue =getCompareDateValue(stopTimeParam);
        if((0==compareStopValue||1==compareStopValue)) {
            if(-1==compareCompleteValue) {
                //结算时间开始 结算完成之间 可以提交 申请修改

            } else {
                //超过结算时间不做任何操作
                r.setCode(HttpResultEnum.COMPLETE_TIME_CODE_1.getCode());
                r.setMsg(HttpResultEnum.COMPLETE_TIME_CODE_1.getMessage());
            }
        }  else if(-1==compareStopValue) {
            //结算时间未开始之前可以编辑、批量导入、提交

        }
        return r;
    }

    /**
     * 考勤结算完成时间点  -1 小于 0 等于 1 大于
     * @return
     */
    @Override
    public Result judgeWorkattendanceCompleteTime(Integer projectId) {
        Result r = new Result();
        String completeTimeParam = getCompleteParam(projectId);
        int compareValue = getCompareDateValue(completeTimeParam);
        if(0==compareValue || 1==compareValue) {
            r.setCode(HttpResultEnum.COMPLETE_TIME_CODE_1.getCode());
            r.setMsg(HttpResultEnum.COMPLETE_TIME_CODE_1.getMessage());
        } else if(-1==compareValue) {
            r.setCode(HttpResultEnum.COMPLETE_TIME_CODE_0.getCode());
            r.setMsg(HttpResultEnum.COMPLETE_TIME_CODE_0.getMessage());
        }
        return r;
    }

    /**
     * 考勤结算时间点  -1 小于 0 等于 1 大于
     * @return
     */
    @Override
    public Result judgeWorkattendanceStopTime(Integer projectId) {
        Result r = new Result();
        String stopTimeParam = getStopTimeParam(projectId);
        int compareValue =getCompareDateValue(stopTimeParam);
        if(0==compareValue || 1==compareValue) {
            r.setCode(HttpResultEnum.STOP_TIME_CODE_1.getCode());
            r.setMsg(HttpResultEnum.STOP_TIME_CODE_1.getMessage());
        } else if(-1==compareValue) {
            r.setCode(HttpResultEnum.STOP_TIME_CODE_0.getCode());
            r.setMsg(HttpResultEnum.STOP_TIME_CODE_0.getMessage());
        }
        return r;
    }

    /**
     * 根据id获得BaTimeParamVo
     * @param id
     * @return
     */
    @Override
    public BaTimeParamVo getTimeParamVoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("enabled",Const.ENABLED_Y);
        return this.baseMapper.getTimeParamVoById(map);
    }

    @Override
    public String getCurrentMonthYear() {
        Calendar cal = Calendar.getInstance();
        String year=String.valueOf(cal.get(Calendar.YEAR));
        String month=String.valueOf(cal.get(Calendar.MONTH)+1);
        return  year+"-"+month;
    }



    /**
     * 当前日期和结算时间点进行比较  -1 小于 0 等于 1 大于
     * @param timeParam
     * @return
     */
    private Integer getCompareDateValue(String timeParam){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int compareValue = -2;
        try{
            Date timeParamDate = df.parse(timeParam);
            Date nowDate = new Date();
            df.format(nowDate);
            compareValue = nowDate.compareTo(timeParamDate);
        } catch (Exception e) {

        }
        return compareValue;
    }
    /**
     * 根据时间点参数 返回结算时间
     * @param param
     * @param projectId
     * @return
     */
    private String getTimeParamValue(Integer projectId,String param){
        // 需要处理超过当前月天数，取当前月最后一天
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("type",param);
        map.put("enabled",Const.ENABLED_Y);
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String currentMonth=String.valueOf(cal.get(Calendar.MONTH)+1);
        String day=this.baseMapper.getTimeParamValueByProjectId(map);
        return year+"-"+currentMonth+"-"+day;
    }

}
