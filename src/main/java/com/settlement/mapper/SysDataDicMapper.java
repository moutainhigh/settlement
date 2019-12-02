package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.DataDicCo;
import com.settlement.entity.SysDataDic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.SysDataDicVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-11-19
 */
@Repository
public interface SysDataDicMapper extends BaseMapper<SysDataDic> {

   public List<SysDataDicVo> getDataDicVoByPid(@Param(value="dataDicCo") DataDicCo dataDicCo, Page<SysDataDicVo> page);

}
