package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.DataDicCo;
import com.settlement.entity.SysDataDic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.SysDataDicListVo;
import com.settlement.vo.SysDataDicVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

   public List<SysDataDicVo> getDataDicVoByPid(@Param(value="dataDicCo") DataDicCo dataDicCo);

   /**
    * @description 根据父CODE查询下拉框
    *
    * @auth admin
    * @date 2019-11-29
    * @param map
    * @return
    */
   List<SysDataDicVo> getDataDicSelectByParentCode(Map<String, Object> map);

   /**
    * 得到根结点数据
    * @param map
    * @return
    */
   @Select("select * from sys_data_dic where dic_code=#{rootCode} and del_flag=#{delFlag} and enabled=#{enabled}")
   SysDataDic getRoot( Map<String, Object> map);

   List<SysDataDicListVo> getDataDicList(Map<String, Object> mapkey);

   @Select("select max(sort) from sys_data_dic where pid=#{pid}")
    Integer getChildSort(String pid);
}
