package com.settlement.service.impl;

import com.settlement.entity.SysRole;
import com.settlement.mapper.SysRoleMapper;
import com.settlement.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> findRoleByUserId(Integer userId) {
        return this.baseMapper.getSysRoleByUserId(userId);
    }

    @Override
    public Result getRolesByDeptId(Integer deptId) {
        Result r = new Result(HttpResultEnum.CODE_200.getCode(), HttpResultEnum.CODE_200.getMessage());
        r.setData(this.baseMapper.getRolesByDeptId(deptId));
        return r;
    }
}
