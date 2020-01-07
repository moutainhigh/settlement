package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.SysUserRole;
import com.settlement.mapper.SysUserRoleMapper;
import com.settlement.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
@Service
@Transactional
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    private static Logger logger = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Override
    public SysUserRole getRoleByUserId(Integer userId) {
        logger.info("根据员工ID取得角色SysUserRoleServiceImpl： getRoleByuserId");
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<SysUserRole>();
        queryWrapper.eq("user_id", userId);
        SysUserRole sur = this.baseMapper.selectOne(queryWrapper);
        return sur;
    }
}
