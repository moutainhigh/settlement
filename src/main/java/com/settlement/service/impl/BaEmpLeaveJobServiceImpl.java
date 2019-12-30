package com.settlement.service.impl;

import com.settlement.entity.BaEmpLeaveJob;
import com.settlement.mapper.BaEmpLeaveJobMapper;
import com.settlement.service.BaEmpLeaveJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.EmpLeaveJobVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-12-30
 */
@Service
@Transactional
public class BaEmpLeaveJobServiceImpl extends ServiceImpl<BaEmpLeaveJobMapper, BaEmpLeaveJob> implements BaEmpLeaveJobService {

    private static Logger logger = LoggerFactory.getLogger(BaEmpLeaveJobServiceImpl.class);

    @Override
    public Result saveEmpLeaveJob(EmpLeaveJobVo empLeaveJobVo) {
        logger.info("员工离场BaEmpLeaveJobServiceImpl: saveEmpLeaveJob");
        Result  r = new Result();
        try {
            empLeaveJobVo.setCreateTime(new Date());
            int ret = this.baseMapper.insert(empLeaveJobVo);
            if (ret > 0) {
                r.setCode(HttpResultEnum.CODE_200.getCode());
                r.setMsg(HttpResultEnum.CODE_200.getMessage());
            }
        } catch (Exception e) {
            logger.error("员工离职BaEmpLeaveJobServiceImpl: saveEmpLeaveJob异常" + e.getMessage());
            e.printStackTrace();
            r.setCode(HttpResultEnum.CODE_500.getCode());
            r.setMsg(HttpResultEnum.CODE_500.getMessage());
        }
        return r;
    }
}
