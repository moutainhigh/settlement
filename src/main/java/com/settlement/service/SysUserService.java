package com.settlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.settlement.co.UserCo;
import com.settlement.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
public interface SysUserService extends IService<SysUser> {

    SysUser findUserByEmail(String email);

}
