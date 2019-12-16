package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.CustomerCo;
import com.settlement.entity.BaCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.BaCustomerAndProjectTreeVo;
import com.settlement.vo.BaCustomerAndProjectVo;
import com.settlement.vo.BaCustomerVo;

import java.util.List;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
public interface BaCustomerService extends IService<BaCustomer> {
    /**加载列表**/
    PageData listPageData(CustomerCo customerCo);
    /**添加 **/
    Result add(BaCustomerVo baCustomerVo);
    /**修改**/
    Result update(BaCustomerVo baCustomerVo);
    /**删除 **/
    Result delete(Integer id);
    /**启用状态 **/
    Result updateEnableStart(Integer id);
    /**停用状态 **/
    Result updateEnableStop(Integer id);
    /**根据id查询得到 BaCustomerVo**/
    BaCustomerVo getBaCustomerVoById(Integer id);
    /**根据用户id查询当前的客户信息和项目组**/
    List<BaCustomerAndProjectVo> getCustomerAndProjectByUserId(Integer userId);
    /**根据用户id查询当前的客户信息和项目组生成树**/
    Object getCustomerAndProjectTreeByUserId(Integer userId);
    /** 根据部门ID取得客户 */
    List<BaCustomerVo> getCustomerByDeptId(Integer deptId);
}
