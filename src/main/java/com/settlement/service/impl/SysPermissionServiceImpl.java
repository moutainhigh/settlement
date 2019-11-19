package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.SysPermission;
import com.settlement.mapper.SysPermissionMapper;
import com.settlement.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.vo.SysPermissionVo;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 功能菜单表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Override
    public List<SysPermissionVo> getMenu(Integer roleId) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<SysPermission>();
        queryWrapper.ne("id",0);
        queryWrapper.eq("type", Const.MENU_TYPE_M);
        queryWrapper.orderByAsc("sort");
        List<SysPermission> list = this.baseMapper.selectList(queryWrapper);
        List<SysPermissionVo> leftList = new ArrayList<SysPermissionVo>();
        if (list != null && list.size() > 0) {
            for (SysPermission sp : list) {
                if (sp.getParentId() == 0) {
                    SysPermissionVo spv = new SysPermissionVo();
                    spv.setId(sp.getId());
                    spv.setPName(sp.getPName());
                    spv.setUrl(sp.getUrl());
                    spv.setPermission(sp.getPermission());
                    spv.setParentId(sp.getParentId());
                    spv.setIcon(sp.getIcon());
                    for (SysPermission sp1 : list) {
                        if (spv.getId() == sp1.getParentId()) {
                            SysPermissionVo spvChild = new SysPermissionVo();
                            spvChild.setId(sp1.getId());
                            spvChild.setPName(sp1.getPName());
                            spvChild.setUrl(sp1.getUrl());
                            spvChild.setPermission(sp1.getPermission());
                            spvChild.setParentId(sp1.getParentId());
                            spvChild.setIcon(sp1.getIcon());
                            spv.getChildrens().add(spvChild);
                        }
                    }
                    leftList.add(spv);
                }
            }
        }
        return leftList;
    }
    @Override
    public List<SysPermissionVo> getMenu() {
        List<SysPermission> list = this.baseMapper.selectList(null);
        List<SysPermissionVo> leftList = new ArrayList<SysPermissionVo>();
        if (list != null && list.size() > 0) {
            for (SysPermission sp : list) {

                SysPermissionVo spv = new SysPermissionVo();
                spv.setCheckArr("0");
                spv.setId(sp.getId());
                spv.setPName(sp.getPName());
                spv.setTitle(sp.getPName());
                spv.setUrl(sp.getUrl());
                spv.setPermission(sp.getPermission());
                spv.setParentId(sp.getParentId());
                spv.setIcon(sp.getIcon());

                leftList.add(spv);

            }
        }
        return leftList;
    }
}
