package com.settlement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaEmpApplyCheck extends Model<BaEmpApplyCheck> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请修改备注
     */
    private String applyRemark;

    /**
     * 申请日期
     */
    private Date applyTime;

    /**
     * 项目组
     */
    private Integer pgId;

    /**
     * 申请人
     */
    private Integer applyUserId;

    /**
     * 审核状态
     */
    private String checkStatus;

    /**
     * 审核人
     */
    private Integer checkUserId;

    /**
     * 审核日期
     */
    private Date checkTime;

    /**
     * 修改口令
     */
    private String updatePassword;

    /**
     * 审核备注
     */
    private String checkRemark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
