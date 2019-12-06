package com.settlement.service;

import com.settlement.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.SysDeptVo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
public interface SysDeptService extends IService<SysDept> {

    Result getDeptSelect();
    /**加载部门树列表**/
    List<SysDeptVo>  getDeptTreeList() ;
    /**添加 **/
    Result add(SysDeptVo sysDeptVo);
    /**修改**/
    Result update(SysDeptVo sysDeptVo);
    /**删除 **/
    Result delete(Integer id);
    /**启用状态 **/
    Result updateEnableStart(Integer id);
    /**停用状态 **/
    Result updateEnableStop(Integer id);
    /**根据id获得SysPermissionVo**/
    SysDeptVo getSysDeptVoById(Integer id);
    /**根据id获得SysPermissionVo根结点信息**/
    SysDeptVo getRootSysDeptVoById(String rootCode);
}
