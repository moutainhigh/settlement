package com.settlement.mapper;

import com.settlement.co.CustomerCo;
import com.settlement.entity.BaCustomer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.BaCustomerAndProjectVo;
import com.settlement.vo.BaCustomerVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@Repository
public interface BaCustomerMapper extends BaseMapper<BaCustomer> {

    @Select("select c.*,dc.dept_id from ba_customer c " +
            "left join ba_dept_customer dc on c.id=dc.customer_id " +
            "where c.id=#{id} and c.del_flag=#{delFlag}"
    )
     BaCustomerVo getBaCustomerVoById(Map<String,Object> map);

    /**
     * 根据用户id查询当前的客户信息和项目组 员工所负责的客户下的项目
     * @param map
     * @return
     */
     List<BaCustomerAndProjectVo> getCustomerAndProjectByUserId(Map<String,Object> map);
    /**客户列表**/
    List<BaCustomerVo> listPageData(CustomerCo customerCo, Page<BaCustomerVo> page);

    /**
     * 根据部门ID取得客户
     *
     * @auth admin
     * @date 2019-12-12
     * @param map
     * @return
     */
    List<BaCustomerVo> getBaCustomerByDeptId(Map<String, Object> map);

}


