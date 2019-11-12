package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.UserCo;
import com.settlement.entity.SysUser;
import com.settlement.mapper.SysUserMapper;
import com.settlement.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser findUserByEmail(String email) {
        return this.baseMapper.getUserByEmail(email);
    }

  /*  @Override
    public IPage<SysUser> findUserList(UserCo userCo) {
        IPage<SysUser> page = new Page<SysUser>(userCo.getPage(), userCo.getLimit());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.like(StringUtils.isNotBlank(userCo.getKeyword()),"real_name",userCo.getKeyword());
        queryWrapper.orderByDesc("create_time");
        return this.baseMapper.selectPage(page, queryWrapper);
    }*/
}
