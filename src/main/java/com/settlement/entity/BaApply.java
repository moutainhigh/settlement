package com.settlement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 申请修改员工信息表
 * </p>
 *
 * @author kun
 * @since 2019-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaApply extends Model<BaApply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String applyType;

    private String applyRemark;

    private String checkStatus;

    private Date applyTime;

    private Integer applyUser;

    private Integer checkUser;

    private Date checkTime;

    private String updatePassword;

    private String checkRemark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
