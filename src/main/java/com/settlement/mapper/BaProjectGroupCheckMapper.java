package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.ProjectGroupCheckCo;
import com.settlement.entity.BaProjectGroupCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.ProjectGroupCheckVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 项目审核表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
@Repository
public interface BaProjectGroupCheckMapper extends BaseMapper<BaProjectGroupCheck> {
    /** 项目组审核列表页 */
    List<ProjectGroupCheckVo> getProjectGroupCheckPageList(@Param(value="projectGroupCheckCo") ProjectGroupCheckCo projectGroupCheckCo, Page<ProjectGroupCheckVo> page);
    /** 根据ID取的项目组审核记录 */
    ProjectGroupCheckVo getPgCheckById(Integer id);
}
