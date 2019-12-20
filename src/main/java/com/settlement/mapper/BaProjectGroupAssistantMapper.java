package com.settlement.mapper;

import com.settlement.entity.BaProjectGroupAssistant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.ProjectGroupAssistantVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
@Repository
public interface BaProjectGroupAssistantMapper extends BaseMapper<BaProjectGroupAssistant> {

    /**
     * <p>
     * @description 批量插入
     * </p>
     *
     * @auth admin
     * @date 2019-11-22
     * @param list
     * @return
     */
    int insertBatch(List<BaProjectGroupAssistant> list);

    /**
     * @description 根据项目组ID查询助理
     *
     * @auth admin
     * @date 2019-12-9
     * @param pgId
     * @return
     */
    List<ProjectGroupAssistantVo> selectAssistantByPgId(Integer pgId);

}
