package com.settlement.service.impl;

import com.settlement.entity.BaEmployee;
import com.settlement.entity.BaPgEmp;
import com.settlement.mapper.BaPgEmpMapper;
import com.settlement.service.BaPgEmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public Result updateEmpSubByBatchId(String ids, Integer pgId) {
        Result r = new Result(HttpResultEnum.CODE_500.getCode(), HttpResultEnum.CODE_500.getMessage());
        try {
            if (ids != null && !"".equals(ids)) {
                List<BaPgEmp> list = new ArrayList<BaPgEmp>();
                BaPgEmp pgEmp = null;
                String[] idArr = ids.split(",");
                for (int i = 0; i < idArr.length; i++) {
                    pgEmp = new BaPgEmp();
                    pgEmp.setEmpId(Integer.valueOf(idArr[i]));
                    pgEmp.setPgId(pgId);
                    pgEmp.setSubStatus(Const.EMP_SUBMIT_STATUS_S);
                    list.add(pgEmp);
                }
                int ret = this.baseMapper.updateEmpSubStatusBatchById(list);
                if (ret > 0) {
                    r.setCode(HttpResultEnum.CODE_200.getCode());
                    r.setMsg(HttpResultEnum.CODE_200.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

}
