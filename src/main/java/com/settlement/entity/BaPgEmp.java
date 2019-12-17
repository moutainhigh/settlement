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
 * @since 2019-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaPgEmp extends Model<BaPgEmp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目组ID
     */
    private Integer pgId;

    /**
     * 项目组员工ID
     */
    private Integer empId;

    /**
     * 入离场状态: I-入场，L-离场
     */
    private String entranceStatus;

    /**
     * 更新时间
     */
    private Date operTime;

    /**
     * 提交状态
     */
    private String subStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
