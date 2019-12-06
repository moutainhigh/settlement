package com.settlement.mapper;

import com.settlement.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.SysDeptVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@Repository
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**根据id获得SysPermissionVo**/
    SysDeptVo getSysDeptVoById(Integer id);
    /**根据id获得SysPermissionVo根结点信息**/
    @Select("select * from sys_dept where dept_code=#{rootCode}")
    SysDeptVo getRootSysDeptVoById(String rootCode);
}
