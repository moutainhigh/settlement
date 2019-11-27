package com.settlement.mapper;

import com.settlement.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.SysPermissionVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 功能菜单表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Select("select * from sys_permission where parent_id=#{parent_id}" )
    public List<SysPermissionVo> getSysPermissionVo(Integer parent_id);
}
