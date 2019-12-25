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
 * 申请员工关联
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaApplyEmployee extends Model<BaApplyEmployee> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer applyId;

    private Integer empId;

    /**
     * 修改状态
     */
    private String updateStatus;

    /**
     * 操作时间
     */
    private Date operTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
