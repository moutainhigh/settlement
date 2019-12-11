package com.settlement.service.impl;

import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCheckCo;
import com.settlement.entity.BaProjectGroupCheck;
import com.settlement.mapper.BaProjectGroupCheckMapper;
import com.settlement.service.BaProjectGroupCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 项目审核表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
@Service
@Transactional
public class BaProjectGroupCheckServiceImpl extends ServiceImpl<BaProjectGroupCheckMapper, BaProjectGroupCheck> implements BaProjectGroupCheckService {
    @Autowired
    private BaProjectGroupCheckMapper baProjectGroupCheckMapper;

    @Override
    public PageData getPgCheckPageList(ProjectGroupCheckCo projectGroupCheckCo) {
        return null;
    }
}
