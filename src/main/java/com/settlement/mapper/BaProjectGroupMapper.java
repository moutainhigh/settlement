package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.ProjectGroupCo;
import com.settlement.entity.BaProjectGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.ProjectGroupVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-20
 */
@Repository
public interface BaProjectGroupMapper extends BaseMapper<BaProjectGroup> {

    /** 项目组列表数据 */
    List<ProjectGroupVo> getProjectGroupList(@Param(value="projectGroupCo") ProjectGroupCo projectGroupCo, Page<ProjectGroupVo> page);
    /**项目组移交**/
    Integer projectApplyTransfer(ProjectGroupVo projectGroupVo);
    /**批量更新项目拥有者人员信息**/
    Integer updateBatchs(List<BaProjectGroup> baProjectGroups);
}
