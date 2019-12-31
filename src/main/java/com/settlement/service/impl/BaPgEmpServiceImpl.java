package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.BaEmployee;
import com.settlement.entity.BaPgEmp;
import com.settlement.mapper.BaEmployeeMapper;
import com.settlement.mapper.BaPgEmpMapper;
import com.settlement.service.BaPgEmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-12-11
 */
@Service
@Transactional
public class BaPgEmpServiceImpl extends ServiceImpl<BaPgEmpMapper, BaPgEmp> implements BaPgEmpService {
    private static Logger logger = LoggerFactory.getLogger(BaPgEmpServiceImpl.class);
    @Autowired
    private BaEmployeeMapper baEmployeeMapper;

    @Override
    public Result updateEmpSubByBatchId(String ids, Integer pgId) {
        Result r = new Result(HttpResultEnum.CODE_500.getCode(), HttpResultEnum.CODE_500.getMessage());
        try {
            if (ids != null && !"".equals(ids)) {
                List<BaPgEmp> list = new ArrayList<BaPgEmp>();
                List<BaEmployee> empList = new ArrayList<BaEmployee>();
                BaPgEmp pgEmp = null;
                BaEmployee be = null;
                String[] idArr = ids.split(",");
                for (int i = 0; i < idArr.length; i++) {
                    pgEmp = new BaPgEmp();
                    pgEmp.setEmpId(Integer.valueOf(idArr[i]));
                    pgEmp.setPgId(pgId);
                    pgEmp.setEntranceStatus(Const.ENTRANCE_STATUS_I);
                    pgEmp.setOperTime(new Date());
                    list.add(pgEmp);
                    be = new BaEmployee();
                    be.setId(pgEmp.getEmpId());
                    be.setEntranceStatus(Const.ENTRANCE_STATUS_I);
                    be.setUpdateTime(new Date());
                    empList.add(be);
                }
                int ret = this.baseMapper.updateEmpSubStatusBatchById(list);
                int ret1 = this.baEmployeeMapper.updateBatchEntranceStatusById(empList);
                if (ret > 0 && ret1 > 0) {
                    r.setCode(HttpResultEnum.CODE_200.getCode());
                    r.setMsg(HttpResultEnum.CODE_200.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    @Override
    public BaPgEmp getBaPgEmpByPgIdAndEmpId(Integer pgId, Integer empId, String entranceStatus) {
        logger.info("查询取得项目组员工关联ID，getBaPgEmpByPgIdAndEmpId：项目组ID-" + pgId + ", 员工ID：" + empId);
        BaPgEmp bge = null;
        try {
            QueryWrapper<BaPgEmp> queryWrapper = new QueryWrapper<BaPgEmp>();
            queryWrapper.eq("pg_id",pgId);
            queryWrapper.eq("emp_id", empId);
            queryWrapper.eq("entrance_status", entranceStatus);
            bge = this.baseMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询取得项目组员工关联ID异常" + e.getMessage());
        }
        return bge;
    }
}
