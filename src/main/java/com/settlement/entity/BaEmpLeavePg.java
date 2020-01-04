package com.settlement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *    员工离场
 * </p>
 *
 * @author kun
 * @since 2019-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaEmpLeavePg extends Model<BaEmpLeavePg> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String reason;

    private Integer applyEmpId;
    /**
     * 离场时间
     */
    private Date leavePgTime;

    private Date createTime;


    /** 员工ID */
    private Integer empId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
