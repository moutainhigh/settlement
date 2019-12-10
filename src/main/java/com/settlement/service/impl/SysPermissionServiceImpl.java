package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.settlement.entity.SysPermission;
import com.settlement.entity.SysPermissionRole;
import com.settlement.mapper.SysPermissionMapper;
import com.settlement.mapper.SysPermissionRoleMapper;
import com.settlement.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SysPermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * <p>
 * 功能菜单表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@Service
@Transactional
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysPermissionRoleMapper sysPermissionRoleMapper;

    /**
     * 生成菜单
     * @param roleId
     * @return
     */
    @Override
    public List<SysPermissionVo> getMenu(Integer roleId) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<SysPermission>();
        queryWrapper.ne("id",1);
        queryWrapper.eq("type", Const.MENU_TYPE_M);
        queryWrapper.orderByAsc("sort");
        List<SysPermission> list = this.baseMapper.selectList(queryWrapper);
        List<SysPermissionVo> leftList = new ArrayList<SysPermissionVo>();
        if (list != null && list.size() > 0) {
            for (SysPermission sp : list) {
                if (sp.getParentId() == 1) {
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

    /**
     * 生成菜单树
     * @return
     */
    @Override
    public List<SysPermissionVo> getPermissionTreeData() {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        queryWrapper.orderByAsc("sort");
        List<SysPermission> list = this.baseMapper.selectList(queryWrapper);
        List<SysPermissionVo> leftList = new ArrayList<SysPermissionVo>();
        //添加一个虚拟根结点
       // leftList.add(PermissionRoot());
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


    /**
     * 添加角色时选择的菜单权限
     * @return
     */
    public Map<SysPermission,List<Map<SysPermission,List<SysPermission>>>> getPermissons(){
        Map<SysPermission,List<Map<SysPermission,List<SysPermission>>>> maproot = new HashMap<>();
        //1-取得第一级的权限菜单
        List<SysPermission> sysPermissions=getSysPermissionLists("parent_id",Const.PERMISSION_ROOT_ID);
        for(SysPermission sysPermission:sysPermissions) {
            //2-取得第二级的权限菜单
            List<SysPermission> childSysPermissions = getSysPermissionLists("parent_id",sysPermission.getId());
            List<Map<SysPermission, List<SysPermission>>> list = new ArrayList<>();

            for (SysPermission child : childSysPermissions) {
                Map<SysPermission, List<SysPermission>> map2 = new HashMap<>();
                //3-取得第三级的权限菜单
                List<SysPermission> childSysPermissions2 = getSysPermissionLists("parent_id",child.getId());
                map2.put(child, childSysPermissions2);
                list.add(map2);
            }
            maproot.put(sysPermission, list);
        }
        return maproot;
    }

    /**
     * 遍历所有的SysPermission 并把SysPermissionRole中对应的权限设置状态设置为true
     * @param id
     * @return
     */
    @Override
    public Map<SysPermissionVo, List<Map<SysPermissionVo, List<SysPermissionVo>>>> getPermissons(Integer id) {
        //根据角色id查询SysPermissionRole中对应的权限
        QueryWrapper<SysPermissionRole> queryPermissionRole = new QueryWrapper<>();
        queryPermissionRole.eq("role_id",id);
        List<SysPermissionRole> sysPermissionRoles=sysPermissionRoleMapper.selectList(queryPermissionRole);

        Map<SysPermissionVo,List<Map<SysPermissionVo,List<SysPermissionVo>>>> maproot = new LinkedHashMap<>();
        //遍历所有的SysPermission 并把SysPermissionRole中对应的权限设置状态设置为true
        //1-取得第一级的权限菜单
        List<SysPermissionVo> sysPermissions=getSysPermissionLists2(sysPermissionRoles,Const.PERMISSION_ROOT_ID);
        for(SysPermissionVo sysPermission:sysPermissions) {
            //2-取得第二级的权限菜单
            List<SysPermissionVo> childSysPermissions = getSysPermissionLists2(sysPermissionRoles,sysPermission.getId());
            //此处有bug
            List<Map<SysPermissionVo, List<SysPermissionVo>>> list = new ArrayList<>();
            //遍历二级权限菜单
            for (SysPermissionVo child : childSysPermissions) {
                Map<SysPermissionVo, List<SysPermissionVo>> map2 = new HashMap<>();
                //3-取得第三级的权限菜单
                List<SysPermissionVo> childSysPermissions2 = getSysPermissionLists2(sysPermissionRoles,child.getId());
                map2.put(child, childSysPermissions2);
                list.add(map2);
            }

            maproot.put(sysPermission, list);
        }
        return maproot;
    }

    /**
     * 添加
     * @param sysPermissionVo
     * @return
     */
    @Override
    public Result add(SysPermissionVo sysPermissionVo) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        sysPermissionVo.setCreateTime(new Date());
        sysPermissionVo.setDelFlag(Const.DEL_FLAG_N);
        try {
            Integer ret = this.baseMapper.insert(sysPermissionVo);
            if (ret != null && ret > 0) {
                r = new Result(HttpResultEnum.ADD_CODE_200.getCode(), HttpResultEnum.ADD_CODE_200.getMessage());
                r.setData(sysPermissionVo);
            } else {
                r = new Result(HttpResultEnum.ADD_CODE_500.getCode(), HttpResultEnum.ADD_CODE_500.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  r;
    }

    /**
     * 修改
     * @param sysPermissionVo
     * @return
     */
    @Override
    public Result update(SysPermissionVo sysPermissionVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage()) ;
        UpdateWrapper<SysPermission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",sysPermissionVo.getId());
        try{
            Integer ret = this.baseMapper.update(sysPermissionVo,updateWrapper);
            if(ret!=null && ret>0) {
                r = new Result(HttpResultEnum.EDIT_CODE_200.getCode(),HttpResultEnum.EDIT_CODE_200.getMessage());
                r.setData(sysPermissionVo);
            } else  {
                r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
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
    public Result delete(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage()) ;
        UpdateWrapper<SysPermission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("del_flag",Const.DEL_FLAG_D);
        updateWrapper.eq("id",id);
        try{
            SysPermission sysPermission = this.baseMapper.selectById(id);
            Integer ret = this.baseMapper.update(sysPermission,updateWrapper);
            if (ret!=null && ret>0) {
                r = new Result(HttpResultEnum.DEL_CODE_200.getCode(),HttpResultEnum.DEL_CODE_200.getMessage());
            } else {
                r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 根据id获得SysPermissionVo
     * @param id
     * @return
     */
    @Override
    public SysPermissionVo getSysPermissionVoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("delFlag",Const.DEL_FLAG_N);
        if(1==id){
            return this.getRootSysPermissionVoById(1);
        } else {
            return this.baseMapper.getSysPermissionVoById(map);
        }
    }

    /**
     * 根据id获得SysPermissionVo根结点信息
     * @param id
     * @return
     */
    @Override
    public SysPermissionVo getRootSysPermissionVoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("delFlag",Const.DEL_FLAG_N);
        SysPermissionVo sysPermissionVo = this.baseMapper.getRootSysPermissionVoById(map);
        sysPermissionVo.setParentContent("结算系统");
        return sysPermissionVo;
    }

    /**
     * 根据传入字段和值 生成查询条件 ，并返回查询结果
     * @param column 数据库表字段
     * @param parent_id
     * @return
     */
    private List<SysPermission> getSysPermissionLists(String column,Integer parent_id){
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column,parent_id);
        //取得第一级的权限菜单
        List<SysPermission> sysPermissions=baseMapper.selectList(queryWrapper);
        return sysPermissions;
    }

    /**
     * 修改角色时 sys_permission_role中的数据 更新SysPermissionVo checked为true
     * @param sysPermissionRoles
     * @param parent_id
     * @return
     */
    private List<SysPermissionVo> getSysPermissionLists2(List<SysPermissionRole> sysPermissionRoles,Integer parent_id){
        Map<String,Object> map = new HashMap<>();
        map.put("parentId",parent_id);
        map.put("delFlag",Const.DEL_FLAG_N);
        //根据parent_id取得子权限菜单
        List<SysPermissionVo> sysPermissionVos = sysPermissionMapper.getSysPermissionVo(map) ;
        //遍历对比sys_permission_role中的permission_id与sys_permission中的id相等则为选中状态
        for(SysPermissionVo sysPermissionVo: sysPermissionVos) {
            sysPermissionVo.setTitle(sysPermissionVo.getPName());
           for(SysPermissionRole sysPermissionRole: sysPermissionRoles ) {
               if(sysPermissionVo.getId().equals(sysPermissionRole.getPermissionId())){
                   sysPermissionVo.setChecked(true);
               }
           }
        }
        return sysPermissionVos;
    }
}
