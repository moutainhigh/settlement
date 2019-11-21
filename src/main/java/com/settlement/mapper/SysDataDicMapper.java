package com.settlement.mapper;

import com.settlement.entity.SysDataDic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.SysDataDicVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 数据字典表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-19
 */
@Repository
public interface SysDataDicMapper extends BaseMapper<SysDataDic> {

   public List<SysDataDicVo> getDataDicVoByPid(Integer pid);
}
